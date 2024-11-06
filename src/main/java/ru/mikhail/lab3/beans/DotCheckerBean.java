package ru.mikhail.lab3.beans;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.mikhail.lab3.dbobjects.Result;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static ru.mikhail.lab3.DotChecker.checkDot;

@Named("dotCheckerBean")
@SessionScoped
public class DotCheckerBean implements Serializable {

    private static final long MIN_EXECUTION_TIME_NS = 1;


    @Inject
    private ControllerBean controllerBean;


    public Result checkAndCalculatePoint() {
        long startTime = System.nanoTime();
        boolean result = checkDot(controllerBean.getX(), controllerBean.getY(), controllerBean.getR());
        long endTime = System.nanoTime();
        long executionTime = Math.max(endTime - startTime, MIN_EXECUTION_TIME_NS);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return new Result(controllerBean.getX(), controllerBean.getY(), controllerBean.getR(), result, executionTime, now);
    }


}
