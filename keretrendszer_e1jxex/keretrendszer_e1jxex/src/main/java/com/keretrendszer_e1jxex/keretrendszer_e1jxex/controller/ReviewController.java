package com.keretrendszer_e1jxex.keretrendszer_e1jxex.controller;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.GameDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.PurchaseDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.ReviewDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.UserDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Game;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Purchase;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Review;
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
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    private GameDAO gameDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PurchaseDAO purchaseDAO;
    @Autowired
    public ReviewController(ReviewDAO reviewDAO, PurchaseDAO purchaseDAO) {
        this.reviewDAO = reviewDAO;
        this.purchaseDAO = purchaseDAO;
    }


    @GetMapping("/")
    public String manageReviews(Model model) {
        List<Review> reviews = reviewDAO.findAll();
        model.addAttribute("reviews", reviews);

        List<Game> games = gameDAO.findAll();
        for (Game game : games) {
            List<Review> gameReviews = reviewDAO.findByGame(game);
            double averageRating = gameReviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
            model.addAttribute("avgRating_" + game.getId(), averageRating);

        }

        return "reviews";
    }


    @PostMapping("/create")
    public String createReview(@RequestParam int purchaseId, @RequestParam String reviewText, @RequestParam String rating, Model model) {
        int temprate = Integer.parseInt(rating);

        Purchase purchase = purchaseDAO.findById(purchaseId).get();

        Optional<Review> existingReview = reviewDAO.findReviewByUserAndPurchase(purchase.getUser(), purchase);
        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setComment(reviewText);
            review.setRating(temprate);
            review.setReviewDate(new Timestamp(System.currentTimeMillis()));
            reviewDAO.update(review);
        } else {
            Review review = new Review();
            review.setGame(purchase.getGame());
            review.setUser(purchase.getUser());
            review.setComment(reviewText);
            review.setRating(temprate);
            review.setReviewDate(new Timestamp(System.currentTimeMillis()));
            reviewDAO.save(review);
        }

        return "redirect:/purchases";
    }

    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Optional<Review> review = reviewDAO.findById(id);
        if (review.isPresent()) {
            reviewDAO.delete(review.get().getId());
        }
        return "redirect:/reviews/";
    }



}
