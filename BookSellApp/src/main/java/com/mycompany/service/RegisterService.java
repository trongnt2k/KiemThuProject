/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class RegisterService {
    public boolean  registers(String fn,String ln,String e,String us,String pw) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        try{
        String sql = "INSERT INTO user(firstname,lastname,email,username,password,role) VALUES(?, ?, ?, ?, ?, 1)";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, fn);
        stm.setString(2, ln);
        stm.setString(3, e);
        stm.setString(4, us);
        stm.setString(5, pw);
        
        stm.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(RegisterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
