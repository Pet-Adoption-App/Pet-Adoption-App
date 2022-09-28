package com.company.petadoptionapp;

public class AadhaarModel {
    public String name,dob,gender,mobile;

    public AadhaarModel(String name, String dob, String gender, String mobile) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.mobile = mobile;
    }

    public AadhaarModel(){ }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
