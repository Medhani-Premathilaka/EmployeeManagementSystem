package com.example.employeemanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.Date;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

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
    private TableColumn<employeeData, String> member_co_date;

    @FXML
    private TableColumn<employeeData, String> member_co_empId;

    @FXML
    private TableColumn<employeeData, String>member_co_fname;

    @FXML
    private TableColumn<employeeData, String> member_co_gender;

    @FXML
    private TableColumn<employeeData, String> member_co_lname;

    @FXML
    private TableColumn<employeeData, String> member_co_phn;

    @FXML
    private TableColumn<employeeData, String> member_co_position;

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
    private Button username;

    @FXML
    private TableView<employeeData> member_tableView;

//    @FXML
//    void close(ActionEvent event) {
//
//    }


    private Connection conn;
    private Statement stmt;
    private PreparedStatement pr;
    private ResultSet rs;
    private Image image;


    public void addEmployeeAdd(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "insert into employee" +
                "(employee_id,firstname,lastname,gender,phon_number,position,date,image)" + "values(?,?,?,?,?,?,?)";
        conn = DBconnct.connect();
        try{

            if(member_empId.getText().isEmpty() || member_fname.getText().isEmpty() || member_lname.getText().isEmpty() || member_gender.getSelectionModel().getSelectedItem() == null || member_phn.getText().isEmpty() || member_position.getSelectionModel().getSelectedItem() == null || getData.path == null) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Please fill all fields");
                a.showAndWait();

            }else{
                String check = "select employee_id from employee where eployee_id = '" + member_empId.getText() + "'";

                stmt = conn.createStatement();
                rs = stmt.executeQuery(check);

                if(rs.next()){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText(null);
                    a.setContentText("Employee ID " + member_empId.getText()+"already exist");
                    a.showAndWait();

                }
            }



            pr = conn.prepareStatement(sql);
            pr.setString(1, member_empId.getText());
            pr.setString(2, member_fname.getText());
            pr.setString(3, member_lname.getText());
            pr.setString(4,(String) member_gender.getSelectionModel().getSelectedItem());
            pr.setString(5, member_phn.getText());
            pr.setString(6, (String) member_position.getSelectionModel().getSelectedItem());

            String uri = getData.path;
            uri = uri.replace("\\", "\\\\");
            pr.setString(7, uri);
            pr.setDate(8, sqlDate);
            pr.executeUpdate();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information msg");
            a.setHeaderText(null);
            a.setContentText("successfully added");
            a.showAndWait();

            addEmployeeShowListData();
            addEmployeeReset();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void  addEmployeeReset(){

        member_empId.setText("");
        member_fname.setText("");
        member_lname.setText("");
        member_gender.getSelectionModel().clearSelection();
        member_position.getSelectionModel().clearSelection();
        member_phn.setText("");
        member_img.setImage(null);

        getData.path = "";
    }
//    public void addEmployeeInsertImage(){
//
//        FileChooser open = new FileChooser();
//        File file = open.showOpenDialog(home_form.getScene().getWindow());
//
//        if(file != null){
//            getData.path = file.getAbsolutePath();
//
//            image = new Image(file.toURI.toString,101,127,false,true);member_img.setImage(image);
//        }
//    }
public void addEmployeeInsertImage() {
    FileChooser open = new FileChooser();
    File file = open.showOpenDialog(home_form.getScene().getWindow());

    if (file != null) {
        getData.path = file.getAbsolutePath();

        // Correct usage of toURI().toString()
        image = new Image(file.toURI().toString(), 101, 127, false, true);
        member_img.setImage(image);
    }
}

private String[] positionList = {"Market Coordinator", "web developer","project manager"};
public void addEmployeePositionList(){

    List<String> listP = new ArrayList<>();

    for(String data: positionList){
        listP.add(data);
    }
    ObservableList listData = FXCollections.observableArrayList(listP);
    member_position.setItems(listData);
}

private String[] listGender = {"male","female","other"};
public void addEmployeeGenderList(){
    List<String> listG = new ArrayList<>();

    for(String data: positionList){
        listG.add(data);
    }
    ObservableList listData = FXCollections.observableArrayList(listG);
    member_gender.setItems(listData);
}

    public ObservableList<employeeData> addEmployeeList(){
        ObservableList<employeeData> listdata = FXCollections.observableArrayList();
        String sql = "select * from employeeData";

        conn = DBconnct.connect();

        try{

            pr = conn.prepareStatement(sql);
            rs = pr.executeQuery();
            employeeData emp;

            while(rs.next()){
                emp = new employeeData(rs.getString("employee_id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("gender"),rs.getString("phon_number"),rs.getString("position"),rs.getString("date"));

                listdata.add(emp);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listdata;
    }

//private ObservableList<employeeData> addEmployeeList;
//    public void  addEmployeeShowListData(){
//        addEmployeeList = addEmployeeShowListData();
//
//        member_co_empId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
//        member_co_empId.setCellValueFactory(new PropertyValueFactory<>("firstname"));
//        member_co_empId.setCellValueFactory(new PropertyValueFactory<>("lastname"));
//        member_co_empId.setCellValueFactory(new PropertyValueFactory<>("gender"));
//        member_co_empId.setCellValueFactory(new PropertyValueFactory<>("phoneumber"));
//        member_co_empId.setCellValueFactory(new PropertyValueFactory<>("position"));
//        member_co_empId.setCellValueFactory(new PropertyValueFactory<>("date"));
//
//       // member_tableView.setItem(addEmployeeList);
//
//    }
private ObservableList<employeeData> addEmployeeList;

    public void addEmployeeShowListData() {
        // Fetch the employee list
        addEmployeeList = addEmployeeList();

        // Set the cell value factories for each column
        member_co_empId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        member_co_fname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        member_co_lname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        member_co_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        member_co_phn.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        member_co_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        member_co_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Set the data in the table view
        member_tableView.setItems(addEmployeeList);
    }
//    public void addEmployeeSelect(){
//        employeeData emp = member_tableView.getSelectionModel().getSelctedItem();
//        int num = member_tableView.getSelectionModel().getSelectedIndex();
//
//
//        member_empId.setText(valueOf(emp.getEmployeeId()));
//        member_empId.setText(valueOf(emp.getFirstName()));
//        member_empId.setText(valueOf(emp.getLastName()));
//        member_empId.setText(valueOf(emp.getPhonenumber()));
//
//        String url = "file:"+ emp.getImage();
//        image = new Image(uri,101,127,false,true);
//        member_img.setImage(image);
//
//    }
public void addEmployeeSelect() {
    // Get the selected employee from the table
    employeeData emp = member_tableView.getSelectionModel().getSelectedItem();
    int num = member_tableView.getSelectionModel().getSelectedIndex();

    if (emp != null) {
        // Set the fields with the selected employee's data
        member_empId.setText(valueOf(emp.getEmployeeId()));
        member_fname.setText(valueOf(emp.getFirstName()));
        member_lname.setText(valueOf(emp.getLastName()));
        member_phn.setText(valueOf(emp.getPhonenumber()));

        // Load the image
        String url = "file:" + emp.getImage();
        image = new Image(url, 101, 127, false, true);
        member_img.setImage(image);
    }
}
    public void displaUsername(){
        username.setText(getData.username);
    }

    public void switchForm(ActionEvent event){

        if(event.getSource() == btnhome){
            home_form.setVisible(true);
            member_form.setVisible(false);
            salary_form.setVisible(false);

            btnhome.setStyle("-fx-background-color:  #cc9900");
            btnaddmember.setStyle("-fx-background-color:  transparent");
            btnsalary.setStyle("-fx-background-color:  transparent");

        }else if(event.getSource() == btnaddmember){
            home_form.setVisible(false);
            member_form.setVisible(true);
            salary_form.setVisible(false);

            btnhome.setStyle("-fx-background-color:  transparent");
            btnaddmember.setStyle("-fx-background-color:  #cc9900");
            btnsalary.setStyle("-fx-background-color:  transparent");

            addEmployeePositionList();
            addEmployeeGenderList();


        }else if(event.getSource() == btnsalary){
            home_form.setVisible(false);
            member_form.setVisible(false);
            salary_form.setVisible(true);

            btnhome.setStyle("-fx-background-color:  transparent");
            btnaddmember.setStyle("-fx-background-color:  transparent");
            btnsalary.setStyle("-fx-background-color:  #cc9900");
        }
    }

    public void logout(){

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Confirmation Message");
        a.setHeaderText(null);
        a.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = a.showAndWait();
        try {
            if(option.get().equals(ButtonType.OK)){
                Stage stage = (Stage) btnlogout.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void close(){
        System.exit(0);
    }
    public void minimize(){
        Stage stage = (Stage)home_form.getScene().getWindow();
        //stage.setIconified(true);;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addEmployeeShowListData();
        addEmployeeGenderList();
        addEmployeePositionList();
    }
}
