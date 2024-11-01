package ru.mikhail.lab3.dbcommunication;

import ru.mikhail.lab3.dbobjects.Result;

import java.util.List;

public interface ResultDAO {
    public Result findById(int id);
    public void save(Result result);
    public void update(Result result);
    public void delete(Result result);
    public List<Result> findAll();
}
