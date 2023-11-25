package com.example.mystory.controller;

import com.example.mystory.config.AuthenticationFacade;
import com.example.mystory.model.dto.LoginModel;
import com.example.mystory.service.security.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcController {

    @GetMapping("public/login")
    public String login(Model model) {
        model.addAttribute("employee", new LoginModel());
        return "login";
    }

    @PostMapping("/admin")
    public String testMvc(HttpServletRequest request, LoginModel loginModel) {
        return null;
    }

}
