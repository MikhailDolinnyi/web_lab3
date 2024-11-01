package ru.mikhail.lab3.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import ru.mikhail.lab3.DotChecker;
import ru.mikhail.lab3.dbcommunication.ResultService;
import ru.mikhail.lab3.dbobjects.Result;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Named("formBean")
@SessionScoped

public class FormBean implements Serializable {

    private static final long MIN_EXECUTION_TIME_NS = 1;
    private float x;
    private float y;
    private float r;
    private boolean result;
    private final ResultService resultService = new ResultService();
    private long executionTime;
    private Timestamp now;


    // Getters and Setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getR() {
        return r;
    }

    public void setR(Float r) {
        this.r = r;
    }

    public boolean getResult() {
        return result;
    }


    public void checkPointAndSaveAtDB() {

        checkPoint();
        saveAtDB();

    }



    public void checkPoint(){
        long startTime = System.nanoTime();
        result = DotChecker.checkDot(getX(), getY(), getR());
        long endTime = System.nanoTime();
        executionTime = Math.max(endTime - startTime, MIN_EXECUTION_TIME_NS);
        now = Timestamp.valueOf(LocalDateTime.now());
    }

    public void saveAtDB() {
        Result result1 = new Result(x, y, r, result, executionTime, now);
        resultService.saveResult(result1);
    }

    public List<Result> getResultList() {
        return resultService.findAllResults();
    }


}
