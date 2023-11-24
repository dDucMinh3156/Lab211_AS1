/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Ta Minh Duc
 */
public class InfoPassenger {

    private String namePassenger;
    private Date birthday;
    private String gender;
    private String phoneNumber;
    private String email;
    private String uniqueID;

    public InfoPassenger() {
    }

    public InfoPassenger(String namePassenger, Date birthday, String gender, String phoneNumber, String email, String uniqueID) {
        this.namePassenger = namePassenger;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.uniqueID = uniqueID;
    }

    public String getNamePassenger() {
        return namePassenger;
    }

    public void setNamePassenger(String namePassenger) {
        this.namePassenger = namePassenger;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniqueID() {
        return uniqueID;
    }

//    public void setUniqueID(String uniqueID) {
//        this.uniqueID = uniqueID;
//    }
    @Override
    public String toString() {
        return namePassenger + ", " + birthday + ", " + gender + ", " + phoneNumber + ", " + ", " + email + ", " + uniqueID;
    }

}
