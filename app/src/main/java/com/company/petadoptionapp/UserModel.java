package com.company.petadoptionapp;

public class UserModel {
    public String userName, userDob, userMobile, userGender , userMail, image;
    UserModel(){

    }

    public UserModel(String userName, String userDob, String userMobile, String userGender, String userMail, String image) {
        this.userName = userName;
        this.userDob = userDob;
        this.userMobile = userMobile;
        this.userGender = userGender;
        this.userMail = userMail;
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
