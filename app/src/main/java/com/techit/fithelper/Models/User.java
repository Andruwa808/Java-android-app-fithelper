package com.techit.fithelper.Models;

public class User {
    private String email, pass, rost, ves, name;

    public User() {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.rost = rost;
        this.ves = ves;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRost() {
        return rost;
    }

    public void setRost(String rost) {
        this.rost = rost;
    }

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

