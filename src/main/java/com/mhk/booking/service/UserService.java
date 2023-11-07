package com.mhk.booking.service;

import com.mhk.booking.models.User;

import java.util.List;

public interface UserService {

    boolean registerUser(User user) throws Exception;

    boolean verifyEmail(String email, String verificationToken);

    boolean changePassword(User user, String oldPassword, String newPassword);

    User getUserProfile(String userName);

    List<Package> getAvailablePackages(String country);
}
