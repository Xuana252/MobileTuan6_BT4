package com.example.bt4.data;

public class Account {
    private String id;
    private String username;
    private String password;

    public Account(String ID,String USERNAME, String PASSWORD) {
        this.id=ID;
        this.username=USERNAME;
        this.password=PASSWORD;
    }

    public String getId(){
        return this.id;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
}
