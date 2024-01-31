package org.example.dao;

import org.example.entity.RentalEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

public class RentalDAO extends GenericDAO<RentalEntity> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(RentalEntity.class, sessionFactory);
    }

    public RentalEntity getAnyUnreturnedRental() {
        Session session = getCurrentSession();
        NativeQuery<RentalEntity> query =
                session.createNativeQuery("select * from rental where return_date is null", RentalEntity.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
