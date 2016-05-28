package com.larry.bean;


/**
 * Created on 16/5/7
 */
public class Order {
    private String orderId;
    private String memberId;
    private String amount;
    private String state;

    public String description = "order info";

    public Order(){}

    public Order(String orderId, String memberId, String amount) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.amount = amount;
    }

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public String pay(String orderId){
        return orderId + " pay success";
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
