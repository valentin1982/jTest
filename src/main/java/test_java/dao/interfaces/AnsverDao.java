package test_java.dao.interfaces;

import test_java.model.Ansver;

import java.util.List;

public interface AnsverDao {

    public void create(Ansver ansver);

    public void update(Ansver ansver);

    public Ansver read(Long id);

    public void delete(Ansver ansver);

    public List<Ansver> findAll();
}
