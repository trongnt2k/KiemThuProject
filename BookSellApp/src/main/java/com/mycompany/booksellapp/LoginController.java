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
    void login(ActionEvent event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        
        Statement stm = conn.createStatement();
        String sql="SELECT * FROM user WHERE username = '"+usernm.getText()+"'AND password = '"+passwd.getText()+"';";
        ResultSet rs = stm.executeQuery(sql);
        
        if(rs.next()){
            try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));  
        stage.show();
        }catch(Exception e) {
            e.printStackTrace();
        }
        }else{
            Utils.getBox("LOGIN FAILED", Alert.AlertType.ERROR).show();
        }
        conn.close();
    }
}

