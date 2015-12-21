package com.yurysavchuk.domain;


public class PhoneNumber {
    private int countryCode;
    private int operCode;
    private int number;
    private String type;
    private String comment;
    private Integer id;



    public PhoneNumber(){}

    public PhoneNumber(int countryCode, int operCode, int number, String type, String comment) {
        this.countryCode = countryCode;
        this.operCode = operCode;
        this.number = number;
        this.type = type;
        this.comment = comment;
    }

    public PhoneNumber(Integer id,int countryCode ,int operCode , int number, String type,String comment ) {
        this.id = id;
        this.comment = comment;
        this.type = type;
        this.number = number;
        this.operCode = operCode;
        this.countryCode = countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public void setOperCode(int operCode) {
        this.operCode = operCode;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCountryCode() {

        return countryCode;
    }

    public int getOperCode() {
        return operCode;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
