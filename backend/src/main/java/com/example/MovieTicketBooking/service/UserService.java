package com.example.MovieTicketBooking.service;

import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.model.User;
import com.example.MovieTicketBooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Load user by email (username) for authentication.
     * This method is used by Spring Security.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + username + " not found"));
    }

    /**
     * Fetch user by ID.
     * Throws ResourceNotFoundException if no user is found.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id " + id + " not found!"));
    }

    /**
     * Get all users.
     * Returns a list of users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Delete user by ID.
     * Throws ResourceNotFoundException if no user is found with the given ID.
     */
    public void deleteUserById(long id) {
        userRepository.delete(getUserById(id));
    }

    /**
     * Update a user's details.
     * This method can be extended for further logic.
     */
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        return userRepository.save(existingUser);
    }


}
