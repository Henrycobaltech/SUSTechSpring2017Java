package com.sustech.flightbooking.persistence.Impl;

import com.sustech.flightbooking.domainmodel.EntityBase;
import com.sustech.flightbooking.persistence.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by Henry on 4/24/2017.
 */
public abstract class RepositoryImplBase<T extends EntityBase> implements Repository<T> {

    protected final SessionFactory sessionFactory;

    public RepositoryImplBase(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Query<T> createQuery(Session session) {
        Class<T> type = getGenericType();
        String hql = String.format("from %s", type.getName());
        return session.createQuery(hql, type);
    }

    protected abstract Class<T> getGenericType();

    @Override
    public List<T> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<T> entities = createQuery(session).list();
        session.getTransaction().commit();
        session.close();
        return entities;
    }

    @Override
    public T findById(UUID id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        T entity = session.get(getGenericType(), id);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    @Override
    public void save(T entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(T entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

}
