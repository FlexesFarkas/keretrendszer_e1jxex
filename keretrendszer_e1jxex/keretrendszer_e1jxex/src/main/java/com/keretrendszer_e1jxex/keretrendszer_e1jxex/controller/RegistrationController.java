package com.keretrendszer_e1jxex.keretrendszer_e1jxex.controller;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.RoleDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.UserDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Role;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               @RequestParam String email,
                               @RequestParam int balance,
                               @RequestParam String role) {
        if (!password.equals(confirmPassword)) {
            return "register";
        }
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);

        user.setPassword(encodedPassword);

        user.setEmail(email);
        user.setBalance(balance);

        Role userRole = roleDAO.findByName(role);
        user.setRoles(List.of(userRole));

        userDAO.save(user);

        return "redirect:/plain-login";
    }
}
