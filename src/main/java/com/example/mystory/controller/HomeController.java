package com.example.mystory.controller;

import com.example.mystory.model.dto.LoginModel;
import com.example.mystory.service.security.LoginService;
import com.example.mystory.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("api/v1")
public class HomeController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    LoginService loginService;

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody LoginModel loginModel) throws Exception {
        String token = loginService.login(loginModel,authenticationManager);
        if (token != null) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("LOGIN FAIL", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody LoginModel loginModel) {
        boolean isSuccess = loginService.signUp(loginModel);
        HttpStatus status = isSuccess ?  HttpStatus.OK : HttpStatus.BAD_REQUEST ;
        return ResponseEntity.status(status).body(status);
    }
}
