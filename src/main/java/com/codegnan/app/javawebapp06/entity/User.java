package com.codegnan.app.javawebapp06.entity;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private Credentials credentials;

    public User() {
    }

    public User(int userId, String firstName, String lastName, Credentials credentials) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.credentials = credentials;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}