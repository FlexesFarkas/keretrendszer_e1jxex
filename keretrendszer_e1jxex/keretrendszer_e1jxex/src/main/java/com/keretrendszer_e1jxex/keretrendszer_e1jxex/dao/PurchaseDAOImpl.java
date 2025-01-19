package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Game;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Purchase;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseDAOImpl implements PurchaseDAO {

    private final EntityManager entityManager;

    @Autowired
    public PurchaseDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Purchase purchase) {
        entityManager.persist(purchase);
        entityManager.flush();
    }


    @Override
    public Optional<Purchase> findById(int id) {
        Purchase purchase = entityManager.find(Purchase.class, id);
        return Optional.ofNullable(purchase);
    }
    @Override
    public List<Purchase> findByUser(User user) {
        TypedQuery<Purchase> query = entityManager.createQuery("SELECT p FROM Purchase p WHERE p.user = :user", Purchase.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Purchase> findAll() {
        TypedQuery<Purchase> query = entityManager.createQuery("FROM Purchase", Purchase.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Purchase purchase) {
        if (purchase.getId() != null && entityManager.find(Purchase.class, purchase.getId()) != null) {
            entityManager.merge(purchase);
        } else {
            throw new IllegalArgumentException("Purchase with ID " + purchase.getId() + " does not exist.");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        Purchase purchase = entityManager.find(Purchase.class, id);
        if (purchase != null) {
            entityManager.remove(purchase);
        } else {
            throw new IllegalArgumentException("Purchase with ID " + id + " does not exist.");
        }
    }

    @Override
    public Optional<Purchase> findByUserAndGame(User user, Game game) {
        TypedQuery<Purchase> query = entityManager.createQuery(
                "SELECT p FROM Purchase p WHERE p.user = :user AND p.game = :game", Purchase.class);
        query.setParameter("user", user);
        query.setParameter("game", game);

        List<Purchase> purchases = query.getResultList();
        return purchases.isEmpty() ? Optional.empty() : Optional.of(purchases.get(0));
    }
}
