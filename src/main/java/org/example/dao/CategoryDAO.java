package org.example.dao;

import org.example.entity.CategoryEntity;
import org.hibernate.SessionFactory;

public class CategoryDAO extends GenericDAO<CategoryEntity> {
    public CategoryDAO(SessionFactory sessionFactory) {
        super(CategoryEntity.class, sessionFactory);
    }
}
