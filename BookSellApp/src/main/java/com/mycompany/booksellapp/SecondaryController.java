package com.mycompany.booksellapp;

import booksellapp.Utils;
import com.mycompany.pojo.Customer;
import com.mycompany.service.CustomerService;
import com.mycompany.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SecondaryController {
    @FXML private MenuBar menuBar;

    @FXML private TextField txtID;
    @FXML private TextField txtFirstname;
    @FXML private TextField txtLastname;
    @FXML private TextField txtNumberPhone;
    @FXML private TextField txtAddress;
    @FXML private TableView<Customer> tbCustomers;
    @FXML private TextField txtKeyWord;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("secondary");
    }
    public void initialize(URL url, ResourceBundle rb) {
        
        loadColumns();
        try {
            loadCustomers("");
        } catch (SQLException ex) {
            System.out.println("ERROR" + ex);
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtKeyWord.textProperty().addListener((obj) -> {
            try {
                loadCustomers(txtKeyWord.getText());
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void loadColumns() {
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn colFirstName = new TableColumn("First Name");
        colFirstName.setCellValueFactory(new PropertyValueFactory("firstname"));

        TableColumn colLastName = new TableColumn("Last Name");
        colLastName.setCellValueFactory(new PropertyValueFactory("lastname"));
        
//        TableColumn colFullName = new TableColumn("Full Name");
//        colFullName.setCellValueFactory(new PropertyValueFactory("fullname"));
        
        TableColumn colPhone = new TableColumn("Phone");
        colPhone.setCellValueFactory(new PropertyValueFactory("phone_number"));
        
        TableColumn colAddress = new TableColumn("Address");
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        
        TableColumn colAction = new TableColumn("ACTIONS");
        colAction.setCellFactory(obj -> {
            Button btn = new Button("Xóa");
            btn.setOnAction(evt -> {
                Utils.getBox("Bạn có chắc chắn xóa không?", Alert.AlertType.CONFIRMATION)
                     .showAndWait().ifPresent(b -> {
                         if (b == ButtonType.OK) {
                             Button bt = (Button) evt.getSource();
                             TableCell cell = (TableCell) bt.getParent();
                             Customer q = (Customer)cell.getTableRow().getItem();
                             
                             Connection conn;
                             try {
                                 conn = JdbcUtils.getConn();
                                 CustomerService s = new CustomerService(conn);
                                 
                                 if (s.deleteCustomer(q.getId())) {
                                     Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                                     loadCustomers("");
                                 } else
                                     Utils.getBox("FAILED", Alert.AlertType.ERROR).show();
                                 
                                 conn.close();
                             } catch (SQLException ex) {
                                 Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                             }
                         }
                    });
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });
        
        this.tbCustomers.getColumns().addAll(colId, colFirstName, colLastName, colPhone, colAddress, colAction);
    }
    
    private void loadCustomers(String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        
        CustomerService s = new CustomerService(conn);
        
       tbCustomers.setItems(FXCollections.observableArrayList(s.getCustomers(kw)));
        
        conn.close();
    }
    
     public void addHandler(ActionEvent evt) {
        try {
            Connection conn = JdbcUtils.getConn();
            CustomerService p = new CustomerService(conn);
            
            Customer b = new Customer();
            b.setFullName(txtFirstname.getText() + txtLastname.getText());
            b.setFirstName(txtFirstname.getText());
            b.setLastName(txtLastname.getText());
            b.setPhone_number((txtNumberPhone.getText()));
            b.setAddress(txtAddress.getText());
            
            
            Alert a =new Alert(Alert.AlertType.INFORMATION);
            if (p.addCustomer(b) == true) 
                a.setContentText("SUCCESSFUL");
            else
                a.setContentText("FAILED");
            
            a.show();
            
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
     
     public void updateHandler(ActionEvent evt) throws SQLException {
        Customer p = this.tbCustomers.getSelectionModel().getSelectedItem();
        p.setFirstName(txtFirstname.getText());
        p.setLastName(txtFirstname.getText());
        p.setAddress(txtAddress.getText());
        p.setPhone_number(txtNumberPhone.getText());
        
        
        Connection conn = JdbcUtils.getConn();
         CustomerService s = new CustomerService(conn);
        if (s.updatCustomer(p) == true) {
            Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
            loadCustomers("");
        } else
            Utils.getBox("FAILED", Alert.AlertType.ERROR).show();
        conn.close();
    }
}