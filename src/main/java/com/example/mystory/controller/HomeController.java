package com.example.mystory.controller;

import com.example.mystory.config.AuthenticationFacade;
import com.example.mystory.model.dto.LoginModel;
import com.example.mystory.service.security.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("public/api/v1")
public class HomeController {
    private AuthenticationManager authenticationManager;
    private LoginService loginService;

    public HomeController(AuthenticationManager authenticationManager, LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.loginService = loginService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody @RequestAttribute LoginModel loginModel) throws Exception {
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
    @GetMapping("/test")
    public ResponseEntity testApi() {
        return ResponseEntity.status( HttpStatus.OK).body("OK");
    }

}
