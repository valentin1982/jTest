package test_java.dao.interfaces;

import test_java.model.Task;

import java.util.List;

public interface TaskDao {

     void create(Task paramTask);

     void update(Task paramTask);

     Task read(Long paramLong);

     void delete(Task paramTask);

     List<Task> findAll();

}
