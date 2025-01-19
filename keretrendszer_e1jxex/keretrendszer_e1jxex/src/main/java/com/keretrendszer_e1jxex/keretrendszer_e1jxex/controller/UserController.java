package com.keretrendszer_e1jxex.keretrendszer_e1jxex.controller;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.UserDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userDAO.findAll());
        return "users";
    }

    @PostMapping("/admin/delete-user")
    public String deleteUser(@RequestParam int userId) {
        Optional<User> userOptional = userDAO.findById(userId);
        if (userOptional.isPresent()) {
            userDAO.delete(userOptional.get().getId());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/rename-user")
    public String renameUser(@RequestParam int userId, @RequestParam String newUsername) {
        Optional<User> userOptional = userDAO.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(newUsername);
            userDAO.save(user);
        }
        return "redirect:/admin/users";
    }
}
