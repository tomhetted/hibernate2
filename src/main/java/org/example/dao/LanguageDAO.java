package org.example.dao;

import org.example.entity.LanguageEntity;
import org.hibernate.SessionFactory;

public class LanguageDAO extends GenericDAO<LanguageEntity> {
    public LanguageDAO(SessionFactory sessionFactory) {
        super(LanguageEntity.class, sessionFactory);
    }
}
