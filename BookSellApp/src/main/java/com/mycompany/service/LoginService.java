/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author User
 */
public class LoginService {
        public int logins(String un, String pw) throws SQLException{
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        String sql="SELECT * FROM user WHERE username = '"+un+"'AND password = '"+pw+"';";
        ResultSet rs = stm.executeQuery(sql);
        if(rs.next())
            return rs.getInt("role");
        return 2;
        }   
}
