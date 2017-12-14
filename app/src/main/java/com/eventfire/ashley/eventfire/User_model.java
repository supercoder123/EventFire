package com.eventfire.ashley.eventfire;

import java.io.Serializable;

/**
 * Created by gf on 17-04-2017.
 */
import java.security.PrivateKey;
import java.security.PublicKey;
public class User_model implements Serializable{

    String phoneNum;
  //  String address;
    String email;
    String password;
    String college;
    String classDiv;
    String username;
    String events;
    //PrivateKey privateKey;
    String publicKey;
    public static PublicKey pubKey;
    public static PrivateKey priKey;

    public static final String MY_PREFS = "myprefs";

    public  User_model(){

    }
    public User_model(String phoneNum,
                      String email, String password, String college,
                      String classDiv,String username,String events,String publicKey) {
        this.phoneNum = phoneNum;
       // this.address = address;
        this.email = email;
        this.password = password;
        this.college = college;
        this.classDiv = classDiv;
        this.username=username;
        this.events=events;
       // this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /*public String getAddress() {
        return address;
    }*/

    /*public void setAddress(String address) {
        this.address = address;
    }
*/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClassDiv() {
        return classDiv;
    }

    public void setClassDiv(String classDiv) {
        this.classDiv = classDiv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    /*public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
*/
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

}
