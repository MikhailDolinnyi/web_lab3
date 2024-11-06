package ru.mikhail.lab3.dbobjects;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private float x;

    private float y;
    private float r;
    private boolean result;

    @Column(name = "execution_time")
    private long executionTime;
    @Column(name = "now_time")
    private Timestamp nowTime;

    public Result(float x, float y, float r, boolean result, long executionTime, Timestamp nowTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.executionTime = executionTime;
        this.nowTime = nowTime;
    }

    public Result() {

    }

    public int getId() {
        return id;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public Timestamp getNowTime() {
        return nowTime;
    }

    public void setNowTime(Timestamp nowTime) {
        this.nowTime = nowTime;
    }


    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", result=" + result +
                ", executionTime=" + executionTime +
                ", nowTime=" + nowTime +
                '}';
    }
}
