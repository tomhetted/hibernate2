package org.example.dao;

import org.example.entity.FilmEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDAO extends GenericDAO<FilmEntity> {
    public FilmDAO(SessionFactory sessionFactory) {
        super(FilmEntity.class, sessionFactory);
    }

    public FilmEntity getByIdUsingQuery(Short id) {
        Session session = getCurrentSession();
        Query<FilmEntity> query =
                session.createQuery("select f from FilmEntity f where id = 27", FilmEntity.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public FilmEntity getFirstAvailable() {
        Session session = getCurrentSession();
        Query<FilmEntity> query =
                session.createQuery("select f from FilmEntity f where f.id " +
                        "not in (select distinct film.id from InventoryEntity)");
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
