package DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class DAO<T> {
    private Session session;
    private T type;

    public DAO(Session session, T type) {
        this.session = session;
        this.type = type;
    }

    public void create() {
        try {
            session.beginTransaction();
            session.save(type);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
//            session.close();
        }
    }

    public void update(T typetoUpdate) {
        try {
            session.beginTransaction();
            session.update(typetoUpdate);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(T typeToDelete) {
        try {
            session.beginTransaction();
            session.delete(typeToDelete);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
//            session.close();
        }
    }
}