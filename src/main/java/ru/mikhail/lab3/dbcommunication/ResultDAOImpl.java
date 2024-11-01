package ru.mikhail.lab3.dbcommunication;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.mikhail.lab3.dbobjects.Result;

import java.util.List;

public class ResultDAOImpl implements ResultDAO {
    @Override
    public Result findById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Result.class, id);
        }
    }

    @Override
    public void save(Result result) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(result);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Result result) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(result);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Result result) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.remove(result);
        tx.commit();
        session.close();
    }

    @Override
    public List<Result> findAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return  (List<Result>) session.createQuery("from Result").list();
        }
    }
}
