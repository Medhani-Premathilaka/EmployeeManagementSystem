package com.example.employeemanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Button btnaddmember;

    @FXML
    private Button btnclose;

    @FXML
    private Button btnhome;

    @FXML
    private Button btnlogout;

    @FXML
    private Button btnsalary;

    @FXML
    private Label home_attend;

    @FXML
    private StackedBarChart<?, ?> home_chart;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_inactive;

    @FXML
    private Label home_totemp;

    @FXML
    private AnchorPane lblupper;

    @FXML
    private Button member_add;

    @FXML
    private Button member_clear;

    @FXML
    private TableColumn<?, ?> member_co_date;

    @FXML
    private TableColumn<?, ?> member_co_empId;

    @FXML
    private TableColumn<?, ?> member_co_fname;

    @FXML
    private TableColumn<?, ?> member_co_gender;

    @FXML
    private TableColumn<?, ?> member_co_lname;

    @FXML
    private TableColumn<?, ?> member_co_phn;

    @FXML
    private TableColumn<?, ?> member_co_position;

    @FXML
    private Button member_delete;

    @FXML
    private TextField member_empId;

    @FXML
    private TextField member_fname;

    @FXML
    private AnchorPane member_form;

    @FXML
    private ComboBox<?> member_gender;

    @FXML
    private ImageView member_img;

    @FXML
    private Button member_import;

    @FXML
    private TextField member_lname;

    @FXML
    private TextField member_phn;

    @FXML
    private ComboBox<?> member_position;

    @FXML
    private TextField member_search;

    @FXML
    private Button member_update;

    @FXML
    private Button salary_clear;

    @FXML
    private TableColumn<?, ?> salary_co_empid;

    @FXML
    private TableColumn<?, ?> salary_co_fname;

    @FXML
    private TableColumn<?, ?> salary_co_lname;

    @FXML
    private TableColumn<?, ?> salary_co_position;

    @FXML
    private TableColumn<?, ?> salary_co_salary;

    @FXML
    private TextField salary_empId;

    @FXML
    private Label salary_fname;

    @FXML
    private AnchorPane salary_form;

    @FXML
    private Label salary_lname;

    @FXML
    private Label salary_position;

    @FXML
    private TextField salary_salary;

    @FXML
    private Button salary_update;

    @FXML
    void close(ActionEvent event) {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
