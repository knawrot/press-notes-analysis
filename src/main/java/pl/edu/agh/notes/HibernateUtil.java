package pl.edu.agh.notes;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by Michał Adamczyk.
 */

public class HibernateUtil {

    private SessionFactory sessionFactory;
    private static Logger LOG = Logger.getLogger(HibernateUtil.class);
    private Session currentSession;

    private SessionFactory buildSessionFactory() {
        try {

            if (sessionFactory != null) {
                closeFactory();
            }
            return configureSessionFactory();
        }
        catch (Throwable ex) {
            LOG.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session openSession() {
        buildSessionFactory();
        currentSession = sessionFactory.openSession();
        return currentSession;
    }

    public void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException ignored) {
                LOG.error("Couldn't close SessionFactory", ignored);
            }
        }
    }

    public SessionFactory configureSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration
                .buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public void closeCurrentSession() {
        if (currentSession != null) {
            try {
                currentSession.close();
            } catch (HibernateException ignored) {
                LOG.error("Couldn't close SessionFactory", ignored);
            }
        }
    }
}
