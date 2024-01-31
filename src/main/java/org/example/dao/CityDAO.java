package org.example.dao;

import org.example.entity.CityEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CityDAO extends GenericDAO<CityEntity> {
    public CityDAO(SessionFactory sessionFactory) {
        super(CityEntity.class, sessionFactory);
    }

    public CityEntity getByName(String name) {
        Session session = getCurrentSession();
            Query<CityEntity> query =
                    session.createQuery("select c from CityEntity c where name = :name", CityEntity.class);
            query.setParameter("name", name);
            query.setMaxResults(1);
            return query.getSingleResult();
    }
}
