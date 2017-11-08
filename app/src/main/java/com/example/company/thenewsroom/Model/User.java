package com.example.company.thenewsroom.Model;

/**
 * Created by jatin on 6/11/17.
 */

public class User {
    private String firstname;
    private String lastname;
    private String phone;
    private String password;

    public User() {
    }

    public User(String firstname, String lastname, String phone, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
