package DAO;

import Exceptions.DatabaseException;
import models.FinishedMatch;

import java.util.List;

public class FinishedMatchDAO implements BaseDAO<FinishedMatch> {

    @Override
    public FinishedMatch findById(Long id) throws DatabaseException {
        return execute(session -> session.get(FinishedMatch.class, id));
    }

    @Override
    public void save(FinishedMatch entity) throws DatabaseException {
        execute(session -> session.save(entity));
    }

    @Override
    public void update(FinishedMatch entity) throws DatabaseException {
        executeVoid(session -> session.update(entity));
    }

    @Override
    public void delete(FinishedMatch entity) throws DatabaseException {
        executeVoid(session -> session.delete(entity));
    }

    @Override
    public List<FinishedMatch> findAll() throws DatabaseException {
        return execute(session -> session.createQuery("FROM finished_match", FinishedMatch.class).getResultList());
    }

}
