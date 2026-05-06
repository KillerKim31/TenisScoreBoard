package DAO;

import Exceptions.DatabaseException;
import models.FinishedMatch;
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
        return execute(session -> session.createQuery("FROM Player", Player.class).getResultList());
    }

    public Player findByName(String name) throws DatabaseException {
        String hqlCommand = "FROM Player WHERE UPPER(name) LIKE :name";
        List<Player> results = execute(session -> session.createQuery(hqlCommand, Player.class)
                .setParameter("name", "%" + name.toUpperCase().trim() + "%")
                .getResultList());

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

}
