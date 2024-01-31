package org.example.dao;

import org.example.entity.InventoryEntity;
import org.hibernate.SessionFactory;

public class InventoryDAO extends GenericDAO<InventoryEntity> {
    public InventoryDAO(SessionFactory sessionFactory) {
        super(InventoryEntity.class, sessionFactory);
    }
}
