package com.keretrendszer_e1jxex.keretrendszer_e1jxex.controller;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.GameDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.PurchaseDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.UserDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Game;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Controller
public class LoginController {
    @Autowired
    private UserDAO userService;

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private PurchaseDAO purchaseDAO;

    @GetMapping("/showMyLoginPage")
    public String showLogin() {
        return "plain-login";
    }

    @GetMapping("/plain-login")
    public String showLoginpage() {
        return "plain-login";
    }

    @GetMapping("/")
    public String ShowHome(@AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser, Model model) {
        if (securityUser == null) {
            return "redirect:/showMyLoginPage";
        }

        Optional<User> userOptional = userService.findByUsername(securityUser.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            model.addAttribute("userBalance", user.getBalance());

            List<Game> games = gameDAO.findAll();
            model.addAttribute("games", games);

            List<Game> userPurchasedGames = purchaseDAO.findByUser(user).stream()
                    .map(purchase -> purchase.getGame())
                    .collect(Collectors.toList());

            model.addAttribute("userPurchasedGame", userPurchasedGames);

            List<String> gameImages = games.stream()
                    .map(Game::getImagePath)
                    .collect(Collectors.toList());
            model.addAttribute("gameImages", gameImages);
        } else {
            return "redirect:/plain-login";
        }

        return "home";
    }





    @GetMapping("/show-access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }

    @GetMapping("/show-register")
    public String showRegisterPage() {
        return "plain-register";
    }

    @GetMapping("/adminpage")
    public String showadminpage() {
        return "adminpage";
    }
}
