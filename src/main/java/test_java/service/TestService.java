package test_java.service;

import test_java.dao.impl.AnsverHibernateDaoImpl;
import test_java.dao.impl.RazdelHibernateDaoImpl;
import test_java.dao.impl.ResultHibernateDaoImpl;
import test_java.dao.impl.TaskHibernateDaoImpl;
import test_java.dao.interfaces.AnsverDao;
import test_java.dao.interfaces.RazdelDao;
import test_java.dao.interfaces.ResultDao;
import test_java.dao.interfaces.TaskDao;
import test_java.model.Ansver;
import test_java.model.Razdel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Date;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private RazdelDao razdelDao;
    @Autowired
    private ResultDao resultDao;
    @Autowired
    private AnsverDao ansverDao;
    @Autowired
    private TaskDao taskDao;

    public TestService() {
    }

    public TestService(AnsverHibernateDaoImpl ansverHibernateDao, TaskHibernateDaoImpl taskHibernateDao, RazdelHibernateDaoImpl razdelHbernateDao, ResultHibernateDaoImpl resultHibernateDao) {
        this.ansverDao = ansverHibernateDao;
        this.taskDao = taskHibernateDao;
        this.resultDao = resultHibernateDao;
        this.razdelDao = razdelHbernateDao;
    }

    public Date setDateResult(Date date) {
        return new Date();
    }


    public Razdel getRazdelByName(String name) {
        return this.razdelDao.readByName(name);
    }

    public boolean isAnswerCorrect(List<Ansver> ansvers, Integer number) {
        boolean isNumberCorrect = (number.intValue() >= 0) && (number.intValue() < ansvers.size());
        if (!isNumberCorrect) {
            return false;
        }
        Ansver ansver = (Ansver) ansvers.get(number.intValue());
        return ansver.isCor();
    }

    public boolean isAnswerCorrect(List<Ansver> ansvers, JToggleButton[] answers) {
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].isSelected()) {
                if (!isAnswerCorrect(ansvers, Integer.valueOf(i))) {
                    return false;
                }
            } else if (isAnswerCorrect(ansvers, Integer.valueOf(i))) {
                return false;
            }
        }
        return true;
    }
}
