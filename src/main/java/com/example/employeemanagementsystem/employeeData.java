package com.example.employeemanagementsystem;

import java.util.Date;

public class employeeData {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String phonenumber;
    private String position;
    private String image;
    private Date date;
    private String Image;


    public employeeData(String employeeId, String firstName, String lastName, String gender, String phonenumber, String position, String image) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phonenumber = phonenumber;
        this.position = position;
        this.image = image;
        this.date = date;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getPosition() {
        return position;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

}
