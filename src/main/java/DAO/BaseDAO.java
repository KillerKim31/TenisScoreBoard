package DAO;

import java.util.List;

import Exceptions.DatabaseException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public interface  BaseDAO<T> {

    T       findById(Long id) throws DatabaseException;
    void    save(T entity)    throws DatabaseException;
    void    update(T entity)  throws DatabaseException;
    void    delete(T entity)  throws DatabaseException;
    List<T> findAll()         throws DatabaseException;

    default <T> T execute(Function<Session, T> command) throws DatabaseException {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            session.beginTransaction();
            T result = command.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Ошибка при работе с базой данных", e);
        }
    }

    default void executeVoid(Consumer<Session> command) throws DatabaseException {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            command.accept(session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Ошибка при работе с базой данных", e);
        }
    }

}