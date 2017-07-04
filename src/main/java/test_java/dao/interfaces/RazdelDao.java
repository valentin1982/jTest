package test_java.dao.interfaces;

import test_java.model.Razdel;

import java.util.List;

public interface RazdelDao {

     void create(Razdel paramRazdel);

     void update(Razdel paramRazdel);

     Razdel read(Long paramLong);

     Razdel readByName(String paramString);

     void delete(Razdel paramRazdel);

     List<Razdel> findAll();

}
