package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GameDAOImpl implements GameDAO {

    private final EntityManager entityManager;

    @Autowired
    public GameDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Game> findByDeveloperId(int developerId) {
        TypedQuery<Game> query = entityManager.createQuery(
                "SELECT g FROM Game g WHERE g.developer.id = :developerId", Game.class);
        query.setParameter("developerId", developerId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Game game) {
        entityManager.merge(game);
    }

    @Override
    public Optional<Game> findById(int id) {
        Game game = entityManager.find(Game.class, id);
        return Optional.ofNullable(game);
    }

    @Override
    public List<Game> findAll() {
        TypedQuery<Game> query = entityManager.createQuery("FROM Game", Game.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Game game) {
        if (game.getId() != null && entityManager.find(Game.class, game.getId()) != null) {
            entityManager.merge(game);
        } else {
            throw new IllegalArgumentException("Game with ID " + game.getId() + " does not exist.");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        Game game = entityManager.find(Game.class, id);
        if (game != null) {
            entityManager.remove(game);
        } else {
            throw new IllegalArgumentException("Game with ID " + id + " does not exist.");
        }
    }
}
