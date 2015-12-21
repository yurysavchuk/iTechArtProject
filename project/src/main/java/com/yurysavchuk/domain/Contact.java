package com.yurysavchuk.domain;

import java.util.LinkedList;
import java.util.List;


public class Contact {
    public Contact() {
    }

    public Contact(Integer id, String name, String surname, String midleName, String birthday,
                   String sex, String nationality,
                   String maritStat, String webSite, String email, String curWorkplace) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.midleName = midleName;
        this.birthday = birthday;
        this.sex = sex;
        this.nationality = nationality;
        this.maritStat = maritStat;
        this.webSite = webSite;
        this.email = email;
        this.curWorkplace = curWorkplace;

    }

    public Contact(Integer id,String name, String surname, String midleName,
                   String birthday, String sex, String nationality,
                   String maritStat, String webSite, String email,
                   String curWorkplace, Address adr) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.midleName = midleName;
        this.birthday = birthday;
        this.sex = sex;
        this.nationality = nationality;
        this.maritStat = maritStat;
        this.webSite = webSite;
        this.email = email;
        this.curWorkplace = curWorkplace;
        this.address = adr;
    }

    private Integer id;
    private String name;
    private String surname;
    private String midleName;
    private String birthday;
    private String sex;
    private String nationality;
    private String maritStat;
    private String webSite;
    private String email;
    private String curWorkplace;
    private Address address;
    private String pathToImg;
    private List<File> listFile = new LinkedList<>();
    private List<PhoneNumber> phoneNumbers = new LinkedList<>();

    public String getPathToImg() {
        return pathToImg;
    }

    public void setPathToImg(String pathToImg) {
        this.pathToImg = pathToImg;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<File> getListFile() {
        return listFile;
    }

    public void setListFile(List<File> list) {
        this.listFile = list;
    }


    public void setId(Integer id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMidleName(String midleName) {
        this.midleName = midleName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setMaritStat(String maritStat) {
        this.maritStat = maritStat;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCurWorkplace(String curWorkplace) {
        this.curWorkplace = curWorkplace;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMidleName() {
        return midleName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public String getNationality() {
        return nationality;
    }

    public String getMaritStat() {
        return maritStat;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getEmail() {
        return email;
    }

    public String getCurWorkplace() {
        return curWorkplace;
    }

    public Address getAddress() {
        return address;
    }

    public Integer getId() { return id; }

    public String getFullName(){
        return surname + name + midleName;
    }


}
