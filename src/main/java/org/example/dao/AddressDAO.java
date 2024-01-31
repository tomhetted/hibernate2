package org.example.dao;

import org.example.entity.AddressEntity;
import org.hibernate.SessionFactory;

public class AddressDAO extends GenericDAO<AddressEntity> {
    public AddressDAO(SessionFactory sessionFactory) {
        super(AddressEntity.class, sessionFactory);
    }
}
