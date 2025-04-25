package com.example.employeemanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private Button btnclose;

    @FXML
    private Button btnlogin;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    private Connection conn;
    private PreparedStatement prepare;
    private ResultSet rs;

    public  void loginAdmin(){
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        conn = DBconnct.connect();
        System.out.println("connected");
        try{
            prepare = conn.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            rs = prepare.executeQuery();
            Alert a;

            if(username.getText().isEmpty() || password.getText().isEmpty()){
                a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Please fill all fields");
                a.showAndWait();
            }else{
                if(rs.next()){
                    getData.username = username.getText();

                    a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Infromation Message");
                    a.setHeaderText(null);
                    a.setContentText("Successfully logged in");


                    btnlogin.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }else{
                    a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText(null);
                    a.setContentText("Invalid username or password");
                    a.showAndWait();

                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void close(){
        System.out.println("close");
        System.exit(0);
    }
}
