package ru.mikhail.lab3.beans;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.mikhail.lab3.DotChecker;
import ru.mikhail.lab3.dbcommunication.ResultService;
import ru.mikhail.lab3.dbobjects.Result;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Named("dotCheckerBean")
@SessionScoped

public class DotCheckerBean implements Serializable {

    private static final long MIN_EXECUTION_TIME_NS = 1;
    private final ResultService resultService = new ResultService();


    @Inject
    private FormBean formBean;


    public void checkPointAndSaveAtDB() {

        checkPoint();
        saveAtDB();

    }


    public void checkPoint() {

        long startTime = System.nanoTime();
        formBean.setResult(DotChecker.checkDot(formBean.getX(), formBean.getY(), formBean.getR()));
        long endTime = System.nanoTime();
        formBean.setExecutionTime(Math.max(endTime - startTime, MIN_EXECUTION_TIME_NS));
        formBean.setNow(Timestamp.valueOf(LocalDateTime.now()));
    }

    public void saveAtDB() {
        Result result1 = new Result(formBean.getX(), formBean.getY(), formBean.getR(), formBean.getResult(), formBean.getExecutionTime(), formBean.getNow());
        resultService.saveResult(result1);
    }


}
