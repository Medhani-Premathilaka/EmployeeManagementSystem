package com.example.employeemanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController {

    @FXML
    private Button btnaddmember;

    @FXML
    private Button btnhome;

    @FXML
    private Button btnlogout;

    @FXML
    private Button btnsalary;

    public void close(){
        System.exit(0);
    }

}
