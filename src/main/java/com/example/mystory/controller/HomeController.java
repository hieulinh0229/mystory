package com.example.mystory.controller;

import com.example.mystory.model.dto.LoginModel;
import com.example.mystory.model.dto.ResponseDto;
import com.example.mystory.service.security.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        String token = loginService.login(loginModel, authenticationManager);
        if (token != null) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("LOGIN FAIL", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signUp(@Validated @RequestBody LoginModel loginModel, BindingResult bindingResult) throws InterruptedException {

        ResponseDto responseDto = loginService.signUp(loginModel, bindingResult);
        HttpStatus status = !responseDto.isError() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        Thread.sleep(5000);
        return new ResponseEntity<>(responseDto, status);
    }

    @GetMapping("/test")
    public ResponseEntity testApi() {
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

}
