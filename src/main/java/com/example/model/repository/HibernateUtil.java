package com.example.model.repository;

import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Exception e) {
                throw new SessionException("Unable to load session factory.");
            }
        }
        return sessionFactory;
    }

}
