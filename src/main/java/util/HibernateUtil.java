package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.Socket;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();

            // Vérifier si MySQL est accessible sur localhost:3306
            if (!isMySQLRunning()) {
                System.out.println("[HibernateUtil] MySQL non détecté sur localhost:3306. Utilisation automatique de la base H2 en mémoire (mode MySQL) pour une exécution autonome...");
                configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:db_tp_hibernate;DB_CLOSE_DELAY=-1;MODE=MySQL");
                configuration.setProperty("hibernate.connection.username", "sa");
                configuration.setProperty("hibernate.connection.password", "");
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            }
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Échec de la création de la SessionFactory : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static boolean isMySQLRunning() {
        try (Socket socket = new Socket("localhost", 3306)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
