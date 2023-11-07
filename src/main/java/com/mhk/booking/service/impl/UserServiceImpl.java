package com.mhk.booking.service.impl;

import com.mhk.booking.models.User;
import com.mhk.booking.service.UserService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {
    private Map<String, User> users = new HashMap<>();
    private Map<String, String> emailVerificationTokens = new HashMap<>();



    public boolean registerUser(User user) throws Exception {

        String verificationToken = UUID.randomUUID().toString();
        emailVerificationTokens.put(user.getEmail(), verificationToken);
        sendVerificationEmail(user.getEmail(), verificationToken);

        user.setEmailVerified(false);
        users.put(user.getUsername(), user);
        return true;
    }


    public boolean verifyEmail(String email, String verificationToken) {
        if (emailVerificationTokens.containsKey(email) && emailVerificationTokens.get(email).equals(verificationToken)) {
            emailVerificationTokens.remove(email);
            User user = users.get(email);
            user.setEmailVerified(true);
            return true;
        }
        return false;
    }

    private void sendVerificationEmail(String email, String verificationToken) throws Exception {
        try {
            System.out.println("Verification email sent to: " + email);
            System.out.println("Verification link: http://example.com/verify?email=" + email + "&token=" + verificationToken);
        } catch (Exception e) {
            throw new Exception("Email sending failed.");
        }
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return false;
        }

        user.setPassword(newPassword);

        return true;
    }

        public User getUserProfile(String userName) {
            User user = users.get(userName);

            if (user != null) {
                return user;
            }

            return null;
        }

    @Override
    public List<Package> getAvailablePackages(String country) {
        return null;
    }



}
