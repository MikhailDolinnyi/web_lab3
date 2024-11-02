package ru.mikhail.lab3.dbcommunication;

import ru.mikhail.lab3.dbobjects.Result;

import java.util.List;

public interface ResultDAO {
    Result findById(int id);
    void save(Result result);
    void update(Result result);
    void delete(Result result);
    List<Result> findAll();
}
