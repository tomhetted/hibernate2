package org.example.dao;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public abstract class GenericDAO<T> {
    private final Class<T> entityClass;
    private SessionFactory sessionFactory;

    public GenericDAO(final Class<T> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }

    public T getById(final Integer id) {
        Session session = getCurrentSession();
        return session.get(entityClass, id);
    }


    public List<T> getAll(int offset, int limit) {
        Session session = getCurrentSession();
        String hql = "FROM " + entityClass.getName();
        Query<T> query = session.createQuery(hql, entityClass);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    public void saveOrUpdate(final T entity) {
        Session session = getCurrentSession();
        session.saveOrUpdate(entity);

    }

    public void delete(final T entity) {
        Session session = getCurrentSession();
        session.delete(entity);
    }

    public void deleteById(Integer id) {
        Session session = getCurrentSession();
        T actor = session.get(entityClass, id);
        if (actor != null) {
            session.delete(actor);
        }
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
