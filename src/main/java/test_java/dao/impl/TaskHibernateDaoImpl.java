package test_java.dao.impl;

import org.apache.log4j.Logger;
import test_java.dao.interfaces.TaskDao;
import test_java.model.Task;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskHibernateDaoImpl implements TaskDao {
    
    private static Logger log = Logger.getLogger(TaskHibernateDaoImpl.class);
   
    @Autowired
    SessionFactory factory;

    public TaskHibernateDaoImpl(SessionFactory factory)
    {
        this.factory = factory;
    }

    public TaskHibernateDaoImpl() {}

    public void create(Task task)
    {
        Session session = null;
        try
        {
            session = this.factory.openSession();
            session.beginTransaction();
            session.save(task);
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

    public void update(Task task)
    {
        Session session = this.factory.openSession();
        try
        {
            session.beginTransaction();
            session.update(task);
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

    public Task read(Long id)
    {
        Session session = this.factory.openSession();
        return (Task)session.get(Task.class, id);
    }

    public void delete(Task task)
    {
        Session session = null;
        try
        {
            session = this.factory.openSession();
            session.beginTransaction();
            session.delete(task);
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

    public List<Task> findAll()
    {
        Session session = this.factory.openSession();
        Criteria criteria = session.createCriteria(Task.class);
        List task = criteria.list();
        return task;
    }
}

