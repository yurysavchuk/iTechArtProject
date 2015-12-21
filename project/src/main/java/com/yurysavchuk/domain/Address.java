package com.yurysavchuk.domain;

public class Address {
    private Integer id;
    private String country;
    private String city;
    private String street;
    private String house;
    private int flat;
    private int index;

    public Address(){}

    public Address(String country, String city, String street, String house,
                   int flat, int index){
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.index = index;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCountry() {

        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public int getFlat() {
        return flat;
    }

    public int getIndex() {
        return index;
    }

}
