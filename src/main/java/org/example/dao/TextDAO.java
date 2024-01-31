package org.example.dao;

import org.example.entity.TextEntity;
import org.hibernate.SessionFactory;

public class TextDAO extends GenericDAO<TextEntity> {
    public TextDAO(SessionFactory sessionFactory) {
        super(TextEntity.class, sessionFactory);
    }
}
