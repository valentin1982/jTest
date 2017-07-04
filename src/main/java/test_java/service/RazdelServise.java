package test_java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_java.dao.interfaces.RazdelDao;
import test_java.model.Razdel;

import java.util.List;

@Service
public class RazdelServise {

    @Autowired
    private RazdelDao razdelDao;

    public RazdelServise() {

    }

    public RazdelServise(RazdelDao razdelDao) {
        this.razdelDao = razdelDao;
    }

    public List<Razdel> findAll() {
        return razdelDao.findAll();
    }
}
