package ru.mikhail.lab3.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import ru.mikhail.lab3.dbcommunication.ResultService;
import ru.mikhail.lab3.dbobjects.Result;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Named("formBean")
@SessionScoped

public class FormBean implements Serializable {


    private float x;
    private float y;
    private float r;
    private boolean result;
    private long executionTime;
    private Timestamp now;


    private final ResultService resultService = new ResultService();

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

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setNow(Timestamp now) {
        this.now = now;
    }

    public Timestamp getNow() {
        return now;
    }

    public List<Result> getResultList() {
        return resultService.findAllResults();
    }
}
