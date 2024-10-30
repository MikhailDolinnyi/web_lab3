package ru.mikhail.lab3;

import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Named("formBean")
@SessionScoped

public class FormBean implements Serializable {

    private static final long MIN_EXECUTION_TIME_NS = 1;
    private float x;
    private float y;
    private float r;
    private boolean result;


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

    @Inject
    private ResultBean resultBean;

    public String checkPoint() {
        long startTime = System.nanoTime();
        result = DotChecker.checkDot(getX(), getY(), getR());
        long endTime = System.nanoTime();
        long executionTime = Math.max(endTime - startTime, MIN_EXECUTION_TIME_NS);
        String formattedNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ResultList resultDto = new ResultList(x,y,r,executionTime,formattedNow,result);
        resultBean.addResult(resultDto);
        return "null";
    }

    public boolean isResult() {
        return result;
    }

    public boolean resultList() {
        return true;
    }


    public List<ResultList> getResultListFromContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResultBean resultBean = (ResultBean) facesContext.getApplication().getELResolver()
                .getValue(facesContext.getELContext(), null, "resultBean");

        return resultBean.getResultList();
    }
}
