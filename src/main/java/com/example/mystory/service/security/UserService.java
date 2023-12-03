package com.example.mystory.service.security;

import com.example.mystory.model.dto.UserDetailDto;
import com.example.mystory.model.entity.Users;
import com.example.mystory.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userOptional = userRepository.findByUserName(username);
        if (userOptional.isEmpty()) {
            logger.info("USER NOT FOUND");
            return null;
        }
        Users user = userOptional.get();
        return new UserDetailDto(user.getUserName(), user.getPassword(), user.getRole());
    }
}
