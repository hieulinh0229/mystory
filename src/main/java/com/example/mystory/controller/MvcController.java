package com.example.mystory.controller;

import com.example.mystory.config.AuthenticationFacade;
import com.example.mystory.model.dto.LoginModel;
import com.example.mystory.service.security.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MvcController {
    @Autowired
    LoginService loginService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("public/login")
    public String login(Model model) {
        model.addAttribute("employee", new LoginModel());
        return "login";
    }

    @PostMapping("/admin")
    public String testMvc(HttpServletRequest request) {
        try {
            HttpSession httpSession = request.getSession();
            UserDetails userDetails = (UserDetails) httpSession.getAttribute("user");
            Authentication authentication = authenticationFacade.getAuthentication();
            return "index";
        }catch (Exception  e){
            System.out.printf(e.getMessage());
        }
        return null;
    }
}
