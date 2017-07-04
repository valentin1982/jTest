package test_java.dao.interfaces;

import test_java.model.Result;

import java.util.List;

public interface ResultDao {

    void create(Result paramResult);

    void update(Result paramResult);

    Result read(Long paramLong);

    void delete(Result paramResult);


    List<Result> findAll();

}
