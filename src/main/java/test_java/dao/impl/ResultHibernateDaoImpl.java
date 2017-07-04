package test_java.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.*;
import test_java.dao.interfaces.ResultDao;
import test_java.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResultHibernateDaoImpl implements ResultDao {
    private static Logger log = Logger.getLogger(ResultHibernateDaoImpl.class);
    @Autowired
    SessionFactory factory;

    public ResultHibernateDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    public ResultHibernateDaoImpl() {
    }

    public void create(Result results) {
        Session session = null;
        try {
            session = this.factory.openSession();
            session.beginTransaction();
            session.save(results);
            session.getTransaction().commit();
        } catch (HibernateException e) {

            if (session != null) {
                session.getTransaction().rollback();
            }
            log.error(e.getLocalizedMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void update(Result results) {
        Session session = this.factory.openSession();
        try {
            session.beginTransaction();
            session.update(results);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            log.error(e.getLocalizedMessage());
        } finally {
            session.close();
        }
    }

    public Result read(Long id) {
        Session session = this.factory.openSession();
        return (Result) session.get(Result.class, id);
    }

    public void delete(Result results) {
        Session session = null;
        try {
            session = this.factory.openSession();
            session.beginTransaction();
            session.delete(results);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            log.error(e.getLocalizedMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    public List<Result> findAll() {
        Session session = this.factory.openSession();
        Criteria criteria = session.createCriteria(Result.class);
        List result = criteria.list();
        return result;
    }
}

