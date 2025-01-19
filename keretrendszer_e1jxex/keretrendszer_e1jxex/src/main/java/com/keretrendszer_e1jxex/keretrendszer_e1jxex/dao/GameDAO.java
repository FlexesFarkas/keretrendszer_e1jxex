package com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.Game;

import java.util.List;
import java.util.Optional;

public interface GameDAO {
    void save(Game game);
    Optional<Game> findById(int id);
    List<Game> findAll();
    void update(Game game);
    void delete(int id);

    public List<Game> findByDeveloperId(int developerId);
}
