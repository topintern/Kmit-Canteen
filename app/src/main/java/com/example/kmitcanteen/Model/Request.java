package com.example.kmitcanteen.Model;

import java.util.List;

public class Request {
    private String rollno;
    private String name;
    private String total;
    private List<Order> foods;
    private String status;
    private String ready;
    private String paymentState;


    public Request() {

    }

    public Request(String rollno, String name, String total, String status,String paymentState, List<Order> foods) {
        this.rollno = rollno;
        this.name = name;
        this.total = total;
        this.foods = foods;
        this.status = status;
        this.paymentState = paymentState;
    }
    public Request(String rollno, String name, String total, List<Order> foods) {
        this.rollno = rollno;
        this.name = name;
        this.total = total;
        this.foods = foods;
        this.status="0";
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReady() {
        return ready;
    }

    public void setReady(String ready) {
        this.ready = ready;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }
}



