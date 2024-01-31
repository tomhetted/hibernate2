package org.example.dao;

import org.example.entity.StaffEntity;
import org.hibernate.SessionFactory;

public class StaffDAO extends GenericDAO<StaffEntity> {
    public StaffDAO(SessionFactory sessionFactory) {
        super(StaffEntity.class, sessionFactory);
    }
}
