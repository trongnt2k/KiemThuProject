/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.booksellapp;

import booksellapp.Utils;
import com.mycompany.service.JdbcUtils;
import com.mycompany.service.RegisterService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    void ChangeToLogin(ActionEvent event) throws IOException{
        Parent loginView = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        Scene loginViewScene = new Scene(loginView);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(loginViewScene);
        window.show();
    }
    @FXML
    void register(ActionEvent event) throws SQLException{
        RegisterService rs = new RegisterService();
        if(UsrName.getText() == null || UsrName.getText().trim().isEmpty() || PasWord.getText() == null || PasWord.getText().trim().isEmpty())
            Utils.getBox("FILL IN USERNAME AND PASSWORD BOX PLEASE", Alert.AlertType.ERROR).show();
        else if(rs.registers(FirstName.getText(), LastName.getText(), Email.getText(), UsrName.getText(), PasWord.getText()) == true)
            Utils.getBox("REGISTER SUCCESSFUL", Alert.AlertType.INFORMATION).show();
    }
}
