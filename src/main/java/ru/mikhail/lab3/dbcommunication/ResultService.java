package ru.mikhail.lab3.dbcommunication;

import ru.mikhail.lab3.dbobjects.Result;

import java.util.List;

public class ResultService {
    private final ResultDAOImpl resultDAO = new ResultDAOImpl();
    public ResultService() {}

    public Result findResult(int id) {
        return resultDAO.findById(id);
    }

    public void saveResult(Result result) {
        resultDAO.save(result);
    }

    public void updateResult(Result result) {
        resultDAO.update(result);
    }

    public void deleteResult(Result result) {
        resultDAO.delete(result);
    }

    public List<Result> findAllResults() {
        return resultDAO.findAll();
    }


}
