package com.example.MovieTicketBooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.MovieTicketBooking.exception.ResourceNotFoundException;
import com.example.MovieTicketBooking.model.User;
import com.example.MovieTicketBooking.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id " + id + " not found!"));
    }

    public List<User> getAllUsers(){
        return  userRepository.findAll();
    }

    public void deleteUserById(long id){
        userRepository.delete(getUserById(id));
    }

   
}
