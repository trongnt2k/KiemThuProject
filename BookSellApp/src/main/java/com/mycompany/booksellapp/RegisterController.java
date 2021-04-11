/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.booksellapp;

import booksellapp.Utils;
import com.mycompany.service.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author User
 */
public class RegisterController {
    @FXML private TextField FirstName;
    @FXML private TextField LastName;
    @FXML private TextField Email;
    @FXML private TextField UsrName;
    @FXML private PasswordField PasWord;
    @FXML
    void register(ActionEvent event) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        String sql = "INSERT INTO user(firstname,lastname,email,username,password,role) VALUES(?, ?, ?, ?, ?, 1)";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, FirstName.getText());
        stm.setString(2, LastName.getText());
        stm.setString(3, Email.getText());
        stm.setString(4, UsrName.getText());
        stm.setString(5, PasWord.getText());
            
        stm.executeUpdate();
        Utils.getBox("REGISTER SUCCESSFUL", Alert.AlertType.INFORMATION).show();
    }
}
