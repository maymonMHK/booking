package com.mhk.booking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  private boolean emailVerified;

  private int usedCredits;
  private int availableCredits;

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.emailVerified = false;
    this.availableCredits = 0;
  }


  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", 
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public int getUsedCredits() {
    return usedCredits;
  }

  public void setUsedCredits(int usedCredits) {
    this.usedCredits = usedCredits;
  }


  public int getAvailableCredits() {
    return availableCredits;
  }

/*  public List<Package> getPurchasedPackages() {
    // Implement logic to retrieve purchased packages for the user from a data store (e.g., a database).
    // Return the list of purchased packages.
    return fetchPurchasedPackagesFromDataStore();
  }*/

/*  private List<Package> fetchPurchasedPackagesFromDataStore() {
    // Implement database or data store access to fetch the user's purchased packages.
    // Return the list of purchased packages.
    // Example: You might query a database based on the user's ID or username.
    return yourDatabaseQueryResult;
  }*/

  public void addCredits(int creditsToAdd) {
    availableCredits += creditsToAdd;
  }

  public void deductCredits(int creditsToDeduct) {
    availableCredits -= creditsToDeduct;
  }

  public void addPurchasedPackage(Package packageToPurchase) {
  }
}
