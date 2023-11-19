package com.example.mystory.service.security;

import com.example.mystory.config.JwtTokenUtil;
import com.example.mystory.model.dto.LoginModel;
import com.example.mystory.model.entity.User;
import com.example.mystory.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    public String  login(LoginModel loginModel, AuthenticationManager authenticationManager) throws Exception {
        Authentication authentication = authenticate(loginModel.getUserName(), loginModel.getPassword(),authenticationManager);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(authentication);
    }
    private static Authentication authenticate(String username, String password, AuthenticationManager authenticationManager) throws Exception {
        try {
            return  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    public boolean signUp(LoginModel loginModel) {
        String userName = loginModel.getUserName();
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isPresent()) {
            return false;
        } else {
            User user = new User();
            user.setUserName(loginModel.getUserName());
            user.setPassword(passwordEncoder.encode(loginModel.getPassword()));
            user.setRole("ROLE_ADMIN");
            user.setId(1);
            userRepository.save(user);
            return true;
        }
    }
}
