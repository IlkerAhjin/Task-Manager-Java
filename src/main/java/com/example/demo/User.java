package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private String username;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private int id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TaskManager taskManager = new TaskManager();

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}
