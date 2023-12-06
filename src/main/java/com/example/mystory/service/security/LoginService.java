package com.example.mystory.service.security;

import com.example.mystory.config.JwtTokenUtil;
import com.example.mystory.model.dto.LoginModel;
import com.example.mystory.model.dto.ResponseDto;
import com.example.mystory.model.entity.Users;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Objects;
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

    public String login(LoginModel loginModel, AuthenticationManager authenticationManager) throws Exception {
        Authentication authentication = authenticate(loginModel.getUserName(), loginModel.getPassword(), authenticationManager);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(authentication);
    }

    private static Authentication authenticate(String username, String password, AuthenticationManager authenticationManager) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public ResponseDto signUp(LoginModel loginModel, BindingResult bindingResult) {
        ResponseDto rp = new ResponseDto();
        String userName = loginModel.getUserName();
        Optional<Users> userOptional = userRepository.findByUserName(userName);
        loginModel.setPassword(passwordEncoder.encode(loginModel.getPassword()));
        StringBuilder mess = new StringBuilder();
        rp.setData(loginModel);
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                mess.append(Objects.requireNonNull(error.getCodes())[1]).append(error.getDefaultMessage());
                rp.setMess(mess.toString());
                rp.setError(true);
                return rp;
            }
        }
        if (userOptional.isEmpty()) {
            Users user = new Users();
            user.setUserName(loginModel.getUserName());
            user.setPassword(loginModel.getPassword());
            user.setRole("ROLE_ADMIN");
            user.setDeleteFlag(false);
            userRepository.save(user);
            rp.setError(false);
            mess.append("Register success");
            rp.setMess(mess.toString());
            return rp;
        } else {
            mess.append("Registered email");
            rp.setError(true);
            rp.setMess(mess.toString());
            return rp;
        }
    }
}
