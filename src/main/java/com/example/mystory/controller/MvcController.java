package com.example.mystory.controller;

import com.example.mystory.model.dto.LoginModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("public")
public class MvcController {
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("employee", new LoginModel());
        return "login";
    }

    @GetMapping("/test")
    public String testMvc(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        UserDetails userDetails = (UserDetails) httpSession.getAttribute("user");
        return "index";
    }

    @PostMapping(value = "/sign-in")
    public String signIn(LoginModel employee) throws Exception {
        return "index";
    }
}
