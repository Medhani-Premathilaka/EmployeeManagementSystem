package com.example.employeemanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private TableView<employeeData> salary_tableView;

    @FXML
    private TableColumn<employeeData, String> salary_co_empid;

    @FXML
    private TableColumn<employeeData, String> salary_co_fname;

    @FXML
    private TableColumn<employeeData, String> salary_co_lname;

    @FXML
    private TableColumn<employeeData, String> salary_co_position;

    @FXML
    private TableColumn<employeeData, String> salary_co_salary;

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

//  public void homChart(){
//      home_chart.getData().clear();
//
//      String sql = "select date, count(id) from employeedata group by timestamp(date) asc limit ?";
//
//      conn = DBconnct.connect();
//        try{
//            pr = conn.prepareStatement(sql);
//            pr.setInt(1, 5);
//            rs = pr.executeQuery();
//
//            while (rs.next()){
//                home_chart.getData().add(new XYChart.Data(rs.getString("date"), rs.getInt("count(id)")));
//            }
//  }

public void homeTotalEployee(){
    String sql = "select count(id) from employeedata";

    conn = DBconnct.connect();
    int countData = 0;

    try {

        pr = conn.prepareStatement(sql);
        rs = pr.executeQuery();

        while (rs.next()){
            countData = rs.getInt("count(id)");

        }
        home_totemp.setText(String.valueOf(countData));

    }catch (Exception e){
        e.printStackTrace();
    }
}

public void addEmployeeTotalPresent(){
    String sql = "select count(id) form emp_info where salary != '0.0";
    conn = DBconnct.connect();
    int countData = 0;
    try{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        while (rs.next()){
            countData = rs.getInt("count(id)");

        }
        home_totemp.setText(String.valueOf(countData));
    }catch (Exception e){
        e.printStackTrace();
    }
}


public void homeTotalInactive(){
    String sql = "select count(id) from emp_info where salary = '0.0'";

    conn = DBconnct.connect();
    int countData = 0;

    try{
        pr = conn.prepareStatement(sql);
        rs = pr.executeQuery();

        while (rs.next()){
            countData = rs.getInt("count(id)");

        }
        home_inactive.setText(String.valueOf(countData));
    }catch (Exception e){
        e.printStackTrace();
    }

}

public ObservableList<employeeData> salaryListData(){
    ObservableList<employeeData> listdata = FXCollections.observableArrayList();
    String sql = "select * from emp_info";

    conn = DBconnct.connect();

    try{

        pr = conn.prepareStatement(sql);
        rs = pr.executeQuery();
        employeeData emp;

        while(rs.next()){
            emp = new employeeData(rs.getString("employee_id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("position"),rs.getDouble("salary"));

            listdata.add(emp);

        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return listdata;
}

private ObservableList<employeeData> salaryList;

public void salaryShowListData(){

    salaryList = salaryListData();

    salary_co_empid.setCellValueFactory(new PropertyValueFactory("employeeId"));
    salary_co_fname.setCellValueFactory(new PropertyValueFactory("firstname"));
    salary_co_lname.setCellValueFactory(new PropertyValueFactory("lastname"));
    salary_co_position.setCellValueFactory(new PropertyValueFactory("position"));
    salary_co_salary.setCellValueFactory(new PropertyValueFactory("salary"));

    salary_tableView.setItems(salaryList);
}

public void memberSearch() {
    FilteredList<employeeData> filter = new FilteredList<>(addEmployeeList, e -> true);

    filter.setPredicate(predicateEmployeeData -> {
        String newValue = member_search.getText(); // Get the search input
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }
        String searchKey = newValue.toLowerCase();

        if (predicateEmployeeData.getEmployeeId().toString().contains(searchKey)) {
            return true;
        } else if (predicateEmployeeData.getFirstName().toLowerCase().contains(searchKey)) {
            return true;
        } else if (predicateEmployeeData.getLastName().toLowerCase().contains(searchKey)) {
            return true;
        } else if (predicateEmployeeData.getGender().toLowerCase().contains(searchKey)) {
            return true;
        } else if (predicateEmployeeData.getPhonenumber().toLowerCase().contains(searchKey)) {
            return true;
        } else if (predicateEmployeeData.getPosition().toLowerCase().contains(searchKey)) {
            return true;
        } else if (predicateEmployeeData.getDate().toString().contains(searchKey)) {
            return true;
        }
        return false; // Default case
    });

    SortedList<employeeData> sortList = new SortedList<>(filter);
    sortList.comparatorProperty().bind(member_tableView.comparatorProperty());
    member_tableView.setItems(sortList);
}

    public void addEmployeeUpdate(){

        String uri = getData.path;
        uri = uri.replace("\\","\\\\");

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "update employeedata set firstname = '"+ member_fname.getText() +" ' , lastname = '" +member_lname.getText() +" ' , gender = ' " + member_gender.getSelectionModel().getSelectedItem() + " ' , position = ' " +member_position.getSelectionModel().getSelectedItem() + "' phone_number = ' " + member_phn.getText() + " ' , image = ' " + uri + " ' , date = ' " + sqlDate + " ' where employee_id = '" + member_empId.getText() + "'";

        conn = DBconnct.connect();
        try{
            if(member_empId.getText().isEmpty() || member_fname.getText().isEmpty() || member_lname.getText().isEmpty() || member_gender.getSelectionModel().getSelectedItem() == null || member_phn.getText().isEmpty() || member_position.getSelectionModel().getSelectedItem() == null || getData.path == null) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Please fill all fields");
                a.showAndWait();

            }else{
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Are you sure you want to update?");
                Optional<ButtonType> option = a.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                   stmt = conn.createStatement();
                   stmt.executeUpdate(sql);

                   double salary = 0;

                   String checkData = "select * from emp_info where employee_id = '" + member_empId.getText() + "'";

                   pr = conn.prepareStatement(checkData);
                   rs = pr.executeQuery();

                   while (rs.next()){
                       salary = rs.getDouble("salary");
                   }

                   String updateInfo = "update emp_info set firstname = '" + member_fname.getText() + " ' , lastname = '" + member_lname.getText() + " ' , position = ' " + member_position.getSelectionModel().getSelectedItem() + " ' where employee_id = '" + member_empId.getText() + "'";

                   pr = conn.prepareStatement(updateInfo);
                   pr.executeUpdate();

                   a =  new Alert(Alert.AlertType.INFORMATION);
                   a.setTitle("Information Message");
                   a.setHeaderText(null);
                   a.setHeaderText("Successfully Updated!");
                   a.showAndWait();

                    addEmployeeShowListData();
                    addEmployeeReset();
                }


            }

        }catch (Exception e){
                e.printStackTrace();
        }

    }

    public void salaryUpdate() {
        String sql = "update emp_info set salary = '" + salary_salary.getText() + "' where employee_id = '" + salary_empId.getText() + "'";

        try {
            Alert a;
            // Corrected the condition to check if all fields are empty
            if (salary_empId.getText().isEmpty() || salary_fname.getText().isEmpty() || salary_lname.getText().isEmpty() || salary_position.getText().isEmpty()) {
                a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Please fill all fields");
                a.showAndWait();
            } else {
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Information Manage ");
                a.setHeaderText(null);
                a.setContentText("Successfully updated");
                a.showAndWait();

                salaryShowListData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public  void salaryReset(){
    salary_empId.setText("");
    salary_fname.setText("");
    salary_lname.setText("");
    salary_position.setText("");
    salary_salary.setText("");

    salary_tableView.getSelectionModel().clearSelection();
    salary_tableView.refresh();
}

   public void  salarySelect(){
    employeeData empd = salary_tableView.getSelectionModel().getSelectedItem();
    int num = salary_tableView.getSelectionModel().getSelectedIndex();

    if( (num -1) < -1){
        return;
    }

    salary_empId.setText(String.valueOf(empd.getEmployeeId()));
    salary_fname.setText(empd.getFirstName());
    salary_lname.setText(empd.getLastName());
    salary_position.setText(empd.getPosition());
    salary_salary.setText((String.valueOf(empd.getSalary())));
   }

    public  void addEmployeeDelete(){

        String  sql = "delete from employeedata where employee_id = '" + member_empId.getText() + "'";

        conn = DBconnct.connect();

        try{
            if(member_empId.getText().isEmpty() || member_fname.getText().isEmpty() || member_lname.getText().isEmpty() || member_gender.getSelectionModel().getSelectedItem() == null || member_phn.getText().isEmpty() || member_position.getSelectionModel().getSelectedItem() == null || getData.path == null) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Please fill all fields");
                a.showAndWait();

            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Are you sure you want to delete?");
                Optional<ButtonType> option = a.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);

                    String deleteInfo = "delete from emp_info where employee_id = '" + member_empId.getText() + "'";

                    pr = conn.prepareStatement(deleteInfo);
                    pr.executeUpdate();

                    a =  new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Information Message");
                    a.setHeaderText(null);
                    a.setHeaderText("Successfully Updated!");
                    a.showAndWait();

                    addEmployeeShowListData();
                    addEmployeeReset();
                    //memberSearch();
                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addEmployeeAdd(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "insert into employeedata" +
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

                String check = "select employee_id from employeedata where employee_id = '" + member_empId.getText() + "'";

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

            String insertInfo = "inset into emp_info" + "(employee_id,firstname,lastname,position,salary) values(?,?,?,?,?)";

            pr = conn.prepareStatement(insertInfo);
            pr.setString(1, member_empId.getText());
            pr.setString(2, member_fname.getText());
            pr.setString(3, member_lname.getText());
            pr.setString(4, (String) member_position.getSelectionModel().getSelectedItem());
            pr.setDouble(5, 0.0);
            pr.setString(6, String.valueOf(sqlDate));


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

    for(String data: listGender){
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

            homeTotalEployee();
            addEmployeeTotalPresent();
            homeTotalInactive();

        }else if(event.getSource() == btnaddmember){
            home_form.setVisible(false);
            member_form.setVisible(true);
            salary_form.setVisible(false);

            btnhome.setStyle("-fx-background-color:  transparent");
            btnaddmember.setStyle("-fx-background-color:  #cc9900");
            btnsalary.setStyle("-fx-background-color:  transparent");

            addEmployeePositionList();
            addEmployeeGenderList();
            memberSearch();

        }else if(event.getSource() == btnsalary){
            home_form.setVisible(false);
            member_form.setVisible(false);
            salary_form.setVisible(true);

            btnhome.setStyle("-fx-background-color:  transparent");
            btnaddmember.setStyle("-fx-background-color:  transparent");
            btnsalary.setStyle("-fx-background-color:  #cc9900");
            salaryShowListData();
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

        displaUsername();

        homeTotalEployee();
        addEmployeeTotalPresent();
        homeTotalInactive();

        addEmployeeShowListData();
        addEmployeeGenderList();
        addEmployeePositionList();

        salaryShowListData();
    }
}
