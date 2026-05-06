package utils;

import DAO.FinishedMatchDAO;
import DAO.PlayerDAO;
import Exceptions.DatabaseException;
import models.Player;
import models.FinishedMatch;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    private static final FinishedMatchDAO matchesDao = new FinishedMatchDAO();
    private static final PlayerDAO        playerDao  = new PlayerDAO();

    // Тестовые записи для первоначального заполнения таблицы
    static {

        Player player1 = new Player("М. Сафин");
        Player player2 = new Player("Д. Медведев");
        Player player3 = new Player("Н. Давыденко");
        Player player4 = new Player("Ю. Михаил");
        Player player5 = new Player("Е. Кафельников");
        Player player6 = new Player("М. Фунович");
        Player player7 = new Player("С. Кузнецова");

        try {
            playerDao.save(player1);
            playerDao.save(player2);
            playerDao.save(player3);
            playerDao.save(player4);
            playerDao.save(player5);
            playerDao.save(player6);
            playerDao.save(player7);

            matchesDao.save(new FinishedMatch(player1, player2, player1));
            matchesDao.save(new FinishedMatch(player3, player4, player4));
            matchesDao.save(new FinishedMatch(player5, player6, player5));
            matchesDao.save(new FinishedMatch(player7, player5, player5));
            matchesDao.save(new FinishedMatch(player3, player1, player1));
            matchesDao.save(new FinishedMatch(player1, player7, player7));
            matchesDao.save(new FinishedMatch(player4, player3, player4));
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

    }

    private HibernateSessionFactory() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Player.class);
                configuration.addAnnotatedClass(FinishedMatch.class);
                sessionFactory = configuration.buildSessionFactory();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}
