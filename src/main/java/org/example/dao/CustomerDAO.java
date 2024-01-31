package org.example.dao;

import org.example.entity.CustomerEntity;
import org.hibernate.SessionFactory;

public class CustomerDAO extends GenericDAO<CustomerEntity> {
    public CustomerDAO(SessionFactory sessionFactory) {
        super(CustomerEntity.class, sessionFactory);
    }
}
