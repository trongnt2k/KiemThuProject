/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.pojo.Book_catalog;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Admin
 */
public class Book_catalogTester {
    @Test
    public void testQuantity() {
        try {
            Connection conn = JdbcUtils.getConn();
            
            Book_catalogService s = new Book_catalogService();
            List<Book_catalog> cates = s.getBookcatalog();
            
            conn.close();
            
            Assertions.assertTrue(cates.size() >= 3);
        } catch (SQLException ex) {
            Logger.getLogger(Book_catalogTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testUniqueName() {
        try {
            Connection conn = JdbcUtils.getConn();
            
            Book_catalogService s = new Book_catalogService();
            List<Book_catalog> cates = s.getBookcatalog();
            
            List<String> names = new ArrayList<>();
            cates.forEach(c -> names.add(c.getName()));
            
            Set<String> testNames = new HashSet<>(names);
            
            conn.close();
            
            Assertions.assertEquals(names.size(), testNames.size());
        } catch (SQLException ex) {
            Logger.getLogger(Book_catalogTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
