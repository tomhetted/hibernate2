package org.example.dao;

import org.example.entity.CountryEntity;
import org.hibernate.SessionFactory;

public class CountryDAO extends GenericDAO<CountryEntity> {
    public CountryDAO(SessionFactory sessionFactory) {
        super(CountryEntity.class, sessionFactory);
    }
}
