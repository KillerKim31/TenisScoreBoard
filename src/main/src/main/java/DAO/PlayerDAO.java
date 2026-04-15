package DAO;

import Exceptions.DatabaseException;
import models.Player;

import java.util.List;

public class PlayerDAO implements BaseDAO<Player> {

    @Override
    public Player findById(Long id) throws DatabaseException {
        return execute(session -> session.get(Player.class, id));
    }

    @Override
    public void save(Player entity) throws DatabaseException {
        execute(session -> session.save(entity));
    }

    @Override
    public void update(Player entity) throws DatabaseException {
        executeVoid(session -> session.update(entity));
    }

    @Override
    public void delete(Player entity) throws DatabaseException {
        executeVoid(session -> session.delete(entity));
    }

    @Override
    public List<Player> findAll() throws DatabaseException {
        return execute(session -> session.createQuery("FROM player", Player.class).getResultList());
    }

    public Player findByName(String name) throws DatabaseException {
        return execute(session -> session.get(Player.class, name));
    }

}
