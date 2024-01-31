package org.example.dao;

import org.example.entity.ActorEntity;
import org.hibernate.SessionFactory;

public class ActorDAO extends GenericDAO<ActorEntity> {
    public ActorDAO(SessionFactory sessionFactory) {
        super(ActorEntity.class, sessionFactory);
    }


}
