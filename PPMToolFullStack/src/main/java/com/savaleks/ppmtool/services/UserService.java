package com.savaleks.ppmtool.services;

import com.savaleks.ppmtool.domain.User;
import com.savaleks.ppmtool.exceptions.UsernameExistException;
import com.savaleks.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setUsername(newUser.getUsername());
            newUser.setConfirmPassword("matched");
            return userRepository.save(newUser);
        }catch (Exception e){
            throw new UsernameExistException("Username " + newUser.getUsername() + " already exists");
        }
    }

}
