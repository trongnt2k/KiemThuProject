/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.pojo.Book_catalog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Book_catalogService { 
    public List<Book_catalog> getBookcatalog() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT * FROM book_catalog");
        
        List<Book_catalog> re = new ArrayList<>();
        while (r.next()) {
            Book_catalog c = new Book_catalog();
            c.setId(r.getInt("id"));
            c.setName(r.getString("name"));
            c.setNote(r.getString("note"));
            
            re.add(c);
        }
        conn.close();
        return re;
    }
}            

 