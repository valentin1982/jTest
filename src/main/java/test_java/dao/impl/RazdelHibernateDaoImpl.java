package test_java.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import test_java.dao.interfaces.RazdelDao;
import test_java.model.Razdel;
import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RazdelHibernateDaoImpl implements RazdelDao {

    private static Logger log = Logger.getLogger(RazdelHibernateDaoImpl.class);

    @Autowired
    SessionFactory factory;

    public RazdelHibernateDaoImpl(SessionFactory factory) {
        this.factory = factory;

    }

    public RazdelHibernateDaoImpl() {
    }

    public void create(Razdel razdel) {
        Session session = null;
        try {
            session = this.factory.openSession();
            session.beginTransaction();
            session.save(razdel);
            session.getTransaction().commit();
        } catch (HibernateException e) {

            if (session != null) {
                session.getTransaction().rollback();
            }
            log.error("create(Razdel razdel) " + e.getLocalizedMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void update(Razdel razdel) {
        Session session = this.factory.openSession();
        try {
            session.beginTransaction();
            session.update(razdel);
            session.getTransaction().commit();
        } catch (HibernateException e) {

            session.getTransaction().rollback();
            log.error("update(Razdel razdel) " + e.getLocalizedMessage());
        } finally {
            session.close();
        }
    }

    public Razdel read(Long id) {
        Session session = this.factory.openSession();
        Razdel razdel = null;
        try {
            session.get(String.valueOf(razdel), id);
            Criteria criteria = session.createCriteria(Razdel.class);
            criteria.add(Expression.eq("id", id));
            razdel = (Razdel) criteria.list().get(0);
            Hibernate.initialize(razdel.getText());
        } catch (HibernateException e) {
            log.error("read(Razdel razdel) " + e.getLocalizedMessage());
        } finally {
            session.close();
        }
        return razdel;
    }

    public Razdel readByName(String name) {
        Session session = this.factory.openSession();
        Razdel razdel = null;
        try {
            Criteria criteria = session.createCriteria(Razdel.class);
            criteria.add(Restrictions.eq("name", name));
            razdel = (Razdel) criteria.list().get(0);
            Hibernate.initialize(razdel.getTasks());
        } catch (HibernateException e) {
            log.error("readByName(Razdel name) " + e.getLocalizedMessage());

        } finally {
            session.close();
        }
        return razdel;
    }

    public void delete(Razdel razdel) {
        Session session = null;
        try {
            session = this.factory.openSession();
            session.beginTransaction();
            session.delete(razdel);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error("readByName(Razdel name) " + e.getLocalizedMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Razdel> findAll() {
        Session session = null;
        try {
            session = factory.openSession();
            Criteria criteria = session.createCriteria(Razdel.class);
            List razdel = criteria.list();
            return razdel;
        } catch (GenericJDBCException ex) {
            log.error("readByName(Razdel name) " + ex.getLocalizedMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
}