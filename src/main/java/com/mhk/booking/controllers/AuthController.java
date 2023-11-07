package com.mhk.booking.controllers;

import com.mhk.booking.jwt.JwtRequest;
import com.mhk.booking.models.ERole;
import com.mhk.booking.models.Role;
import com.mhk.booking.models.User;
import com.mhk.booking.payload.request.LoginRequest;
import com.mhk.booking.payload.request.SignupRequest;
import com.mhk.booking.payload.response.MessageResponse;
import com.mhk.booking.payload.response.UserInfoResponse;
import com.mhk.booking.repository.RoleRepository;
import com.mhk.booking.repository.UserRepository;
import com.mhk.booking.security.jwt.JwtUtils;
import com.mhk.booking.security.services.UserDetailsImpl;
import com.mhk.booking.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserService userService;
  private Map<String, User> users = new HashMap<>();
  private Map<String, String> emailVerificationTokens = new HashMap<>();


  /*@PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String token = tokenProvider.generateToken(userDetails);

    return ResponseEntity.ok(new JwtAuthenticationResponse(token));
  }*/

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    System.out.println("Testing :");
    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles, jwtCookie.toString())
            );
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws Exception {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
          case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);

            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }
    user.setRoles(roles);
    userRepository.save(user);
    userService.registerUser(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }



  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've been signed out!"));
  }

  @PostMapping("/verifyEmail")
  public void verifyEmail(@RequestBody String email, @RequestBody String verificationToken) throws Exception {
    boolean isVerified = userService.verifyEmail(email, verificationToken);

    if (isVerified) {
      System.out.println("Email verified successfully.");
    } else {
      throw new Exception("Email verification failed. The link may be invalid or expired.");
    }

  }

  @PostMapping("/changePassword")
  public void changePassword(@RequestBody User user,  @RequestBody String oldPassword ,   @RequestBody String newPassword) throws Exception {
    try {
      userService.changePassword(user,oldPassword ,newPassword);
    }catch (Exception e){
       throw new Exception("Password changing failed");
    }

  }

  @PostMapping("/getUserProfile")
  public User getUserProfile( @RequestBody String userName) {
      User userDetail = userService.getUserProfile(userName);
       return userDetail;
  }


  @PostMapping("/getAvailablePackages")
  public List<Package> getAvailablePackages( @RequestBody String country) {
    List<Package> userDetail = userService.getAvailablePackages(country);
    return userDetail;
  }



}


