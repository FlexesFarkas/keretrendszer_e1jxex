package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Game;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Purchase;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Review;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;

import java.util.List;
import java.util.Optional;

public interface ReviewDAO {
    void save(Review review);
    Optional<Review> findById(int id);
    List<Review> findByGame(Game game);
    List<Review> findAll();
    void update(Review review);
    void delete(int id);

    public Optional<Review> findReviewByUserAndPurchase(User user, Purchase purchase);
}
