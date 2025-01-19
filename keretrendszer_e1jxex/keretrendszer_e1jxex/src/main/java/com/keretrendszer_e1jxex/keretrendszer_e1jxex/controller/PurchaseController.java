package com.keretrendszer_e1jxex.keretrendszer_e1jxex.controller;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.GameDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.PurchaseDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.UserDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Game;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Purchase;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseDAO purchaseDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GameDAO gameDAO;

    @PostMapping("/purchase")
    public String purchaseGame(@AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser,
                               @RequestParam int gameId,
                               RedirectAttributes redirectAttributes, Model model) {
        Optional<User> optionalUser = userDAO.findByUsername(securityUser.getUsername());
        Optional<Game> optionalGame = gameDAO.findById(gameId);

        if (optionalUser.isEmpty() || optionalGame.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invalid user or game.");
            return "redirect:/";
        }

        User user = optionalUser.get();
        Game game = optionalGame.get();

        Optional<Purchase> existingPurchase = purchaseDAO.findByUserAndGame(user, game);
        model.addAttribute("userBalance", user.getBalance());
        model.addAttribute("user", user);
        if (existingPurchase.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "You have already purchased this game.");
            return "redirect:/";
        }

        if (user.getBalance() < game.getPrice()) {
            redirectAttributes.addFlashAttribute("error", "Insufficient balance to buy the game.");
            return "redirect:/";
        }

        try {
            user.setBalance(user.getBalance() - game.getPrice());
            userDAO.update(user);

            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setGame(game);
            purchase.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
            purchaseDAO.save(purchase);

            redirectAttributes.addFlashAttribute("message", "Game purchased successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred during the purchase.");
            return "redirect:/";
        }

        return "redirect:/";
    }

    @GetMapping
    public String getUserPurchases(@AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser, Model model) {
        Optional<User> optionalUser = userDAO.findByUsername(securityUser.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Purchase> purchases = purchaseDAO.findByUser(user);
            model.addAttribute("purchases", purchases);

            List<Game> purchasedGames = purchases.stream().map(Purchase::getGame).toList();
            model.addAttribute("userPurchasedGame", purchasedGames);

            List<Purchase> userPurchases = purchaseDAO.findByUser(user);
            model.addAttribute("userPurchases", userPurchases);
            model.addAttribute("userBalance", user.getBalance());
        } else {
            model.addAttribute("error", "User not found.");
        }

        return "purchases";
    }

    @PostMapping("/refund")
    public String refundPurchase(@RequestParam int purchaseId, @AuthenticationPrincipal org.springframework.security.core.userdetails.User securityUser) {
        Optional<User> optionalUser = userDAO.findByUsername(securityUser.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Purchase> optionalPurchase = purchaseDAO.findById(purchaseId);
            if (optionalPurchase.isPresent() && optionalPurchase.get().getUser().getId().equals(user.getId())) {
                Purchase purchase = optionalPurchase.get();
                user.setBalance(user.getBalance() + purchase.getGame().getPrice());
                userDAO.update(user);
                purchaseDAO.delete(purchase.getId());
                return "redirect:/purchases";
            } else {
                return "redirect:/purchases?error=Invalid purchase request.";
            }
        } else {
            return "redirect:/purchases?error=User not found.";
        }
    }
}
