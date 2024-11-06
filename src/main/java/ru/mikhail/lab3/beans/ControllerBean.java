package ru.mikhail.lab3.beans;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.mikhail.lab3.dbcommunication.ResultService;
import ru.mikhail.lab3.dbobjects.Result;

import java.io.Serializable;
import java.util.List;


@Named("controllerBean")
@SessionScoped
@ManagedBean
public class ControllerBean implements Serializable {

    private float x;
    private float y;
    private float r;
    ResultService resultService = new ResultService();

    @Inject
    DotCheckerBean dotCheckerBean;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void completeRequest() {
        resultService.saveResult(dotCheckerBean.checkAndCalculatePoint());

    }


    public List<Result> getResultList() {
        return resultService.findAllResults();
    }


}
