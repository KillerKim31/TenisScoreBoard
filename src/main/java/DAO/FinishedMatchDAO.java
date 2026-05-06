package DAO;

import Exceptions.DatabaseException;
import models.FinishedMatch;
import org.hibernate.query.Query;

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
        String hqlCommand = "FROM FinishedMatch";
        return execute(session -> session.createQuery(hqlCommand, FinishedMatch.class).getResultList());
    }

    public List<FinishedMatch> getAllAtPagination(int limit, int offset) throws DatabaseException {
        String hqlCommand = "FROM FinishedMatch ORDER BY id DESC";
        return execute(session -> session.createQuery(hqlCommand, FinishedMatch.class)
                .setMaxResults(limit).setFirstResult(offset).getResultList());
    }

    public  List<FinishedMatch> getByPlayerNameAtPagination(String filterName, int limit, int offset) throws DatabaseException {
        String hqlCommand = """
                                FROM FinishedMatch
                                WHERE UPPER(player1.name) LIKE :name OR UPPER(player2.name) LIKE :name
                                ORDER BY id DESC
                            """;
        return execute(session -> session.createQuery(hqlCommand, FinishedMatch.class)
                .setParameter("name", "%" + filterName.toUpperCase().trim() + "%")
                .setMaxResults(limit).setFirstResult(offset).getResultList());


    }

    public long getAllUnique() throws DatabaseException {
        return execute(session -> session.createQuery("SELECT count(*) FROM FinishedMatch", Long.class).getSingleResult());
    }

    public long getByPlayerNameUnique(String filterName) throws DatabaseException {
        String hqlCommand = """
                                SELECT count(*)
                                FROM FinishedMatch
                                WHERE UPPER(player1.name) LIKE :name OR UPPER(player2.name) LIKE :name
                            """;
        return execute(session -> session.createQuery(hqlCommand, Long.class)
                .setParameter("name", "%" + filterName.toUpperCase().trim() + "%").getSingleResult());
    }

}
