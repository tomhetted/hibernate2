package org.example.dao;

import org.example.entity.StoreEntity;
import org.hibernate.SessionFactory;

public class StoreDAO extends GenericDAO<StoreEntity> {
    public StoreDAO(SessionFactory sessionFactory) {
        super(StoreEntity.class, sessionFactory);
    }
}
