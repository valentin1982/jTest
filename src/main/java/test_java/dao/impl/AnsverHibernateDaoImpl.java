package test_java.dao.impl;

import org.apache.log4j.Logger;
import test_java.dao.interfaces.AnsverDao;
import test_java.model.Ansver;
import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AnsverHibernateDaoImpl implements AnsverDao {

    private static Logger log = Logger.getLogger(AnsverHibernateDaoImpl.class);

    @Autowired
    SessionFactory factory;

    public AnsverHibernateDaoImpl(SessionFactory factory)
    {
        this.factory = factory;
    }

    public AnsverHibernateDaoImpl() {}

    public void create(Ansver ansver)
    {
        Session session = null;
        try
        {
            session = this.factory.openSession();
            session.beginTransaction();
            session.save(ansver);
            session.getTransaction().commit();
        }
        catch (HibernateException e)
        {
            if (session != null) {
                session.getTransaction().rollback();
            }
            log.error(e.getLocalizedMessage());
        }
        finally
        {
            if (session != null) {
                session.close();
            }
        }
    }

    public void update(Ansver ansver)
    {
        Session session = this.factory.openSession();
        try
        {
            session.beginTransaction();
            session.update(ansver);
            session.getTransaction().commit();
        }
        catch (HibernateException e)
        {
            session.getTransaction().rollback();
            log.error(e.getLocalizedMessage());
        }
        finally
        {
            session.close();
        }
    }

    public Ansver read(Long id)
    {
        Session session = this.factory.openSession();
        Ansver ansver = null;
        try
        {
            session.get(String.valueOf(ansver), id);
            Criteria criteria = session.createCriteria(Ansver.class);
            criteria.add(Expression.eq("id", id));
            ansver = (Ansver)criteria.list().get(0);
            Hibernate.initialize(ansver.getText());
        }
        catch (HibernateException e)
        {
            log.error(e.getLocalizedMessage());
        }
        finally
        {
            session.close();
        }
        return ansver;
    }

    public void delete(Ansver ansver)
    {
        Session session = null;
        try
        {
            session = this.factory.openSession();
            session.beginTransaction();
            session.delete(ansver);
            session.getTransaction().commit();
        }
        catch (HibernateException e)
        {
            if (session != null) {
                session.getTransaction().rollback();
            }
            log.error(e.getLocalizedMessage());
        }
        finally
        {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Ansver> findAll()
    {
        Session session = this.factory.openSession();
        Criteria criteria = session.createCriteria(Ansver.class);
        List ansver = criteria.list();
        return ansver;
    }
}

