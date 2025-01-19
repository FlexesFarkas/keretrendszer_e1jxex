package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Purchase;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Review;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

    private final EntityManager entityManager;

    @Autowired
    public ReviewDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Review review) {
        entityManager.persist(review);
        entityManager.flush();
    }

    @Override
    public Optional<Review> findById(int id) {
        Review review = entityManager.find(Review.class, id);
        return Optional.ofNullable(review);
    }

    @Override
    public List<Review> findAll() {
        TypedQuery<Review> query = entityManager.createQuery("FROM Review", Review.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Review review) {
        if (review.getId() != null && entityManager.find(Review.class, review.getId()) != null) {
            entityManager.merge(review);
            entityManager.flush();
        } else {
            throw new IllegalArgumentException("Review with ID " + review.getId() + " does not exist.");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        Review review = entityManager.find(Review.class, id);
        if (review != null) {
            entityManager.remove(review);
        } else {
            throw new IllegalArgumentException("Review with ID " + id + " does not exist.");
        }
    }
    @Override
    public Optional<Review> findReviewByUserAndPurchase(User user, Purchase purchase) {
        TypedQuery<Review> query = entityManager.createQuery(
                "SELECT r FROM Review r WHERE r.user = :user AND r.game = :game", Review.class);
        query.setParameter("user", user);
        query.setParameter("game", purchase.getGame());
        List<Review> reviews = query.getResultList();
        return reviews.isEmpty() ? Optional.empty() : Optional.of(reviews.get(0));
    }

}
