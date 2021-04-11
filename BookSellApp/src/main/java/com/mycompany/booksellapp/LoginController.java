/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.booksellapp;

import booksellapp.Utils;
import com.mycompany.service.JdbcUtils;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author User
 */
public class LoginController {
    @FXML private GridPane GridPane;
    @FXML private TextField usernm;
    @FXML private PasswordField passwd; 
    @FXML
    void ChangeToRegister(ActionEvent event) throws IOException{
        Parent registerView = FXMLLoader.load(getClass().getResource("register.fxml"));
        
        Scene registerViewScene = new Scene(registerView);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(registerViewScene);
        window.show();
    }
    @FXML
    void login(ActionEvent event) throws SQLException, IOException {
        Connection conn = JdbcUtils.getConn();
        
        Statement stm = conn.createStatement();
        String sql="SELECT * FROM user WHERE username = '"+usernm.getText()+"'AND password = '"+passwd.getText()+"';";
        ResultSet rs = stm.executeQuery(sql);
        
        if(rs.next()){
        Parent pri = FXMLLoader.load(getClass().getResource("primary.fxml"));
        
        Scene scene = new Scene(pri);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
        }else{
            Utils.getBox("LOGIN FAILED", Alert.AlertType.ERROR).show();
        }
        conn.close();
    }
}

