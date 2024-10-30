package ru.mikhail.lab3;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("resultBean")
@SessionScoped // или @ApplicationScoped, если нужен общий доступ
public class ResultBean implements Serializable {

    private List<ResultList> resultList;

    public ResultBean() {
        resultList = new ArrayList<>();
    }

    public List<ResultList> getResultList() {
        return resultList;
    }

    public void addResult(ResultList result) {
        resultList.add(result);
    }
}
