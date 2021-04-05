/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.pojo.Book;
import com.mycompany.pojo.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LHT
 */
public class CustomerService {
    private Connection conn;
    
    public CustomerService (Connection conn){
        this.conn = conn;
    }
    
    public boolean deleteCustomer(int customerId) {
       
        try {
            String sql = "DELETE FROM customer WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, customerId);
            
            int row = stm.executeUpdate();
            
            return row > 0;
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    public List<Customer> getCustomers(String kw) throws SQLException{
        if (kw == null)
            throw new SQLDataException();
        ////CHẠY THỬ RỒI KIỂM TRA LẠI XEM SQL CHẠY ĐÚNG KO. WHERE CHỈ CÓ NAME, TÌM AUTHOR ĐC KO?
        String sql ="SELECT * FROM customer WHERE fullname like concat('%', ?, '%')";
        PreparedStatement stm =this.conn.prepareStatement(sql);
        stm.setString(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while(rs.next()){
            Customer b = new Customer();
            b.setId(rs.getInt("id"));
            b.setFullName(rs.getString("fullname"));
            b.setFirstName(rs.getString("firstname"));
            b.setLastName(rs.getString("lastname"));
            b.setPhone_number(rs.getString("phone_nuber "));
            b.setAddress(rs.getString("address"));
           
            
            customers.add(b);
        }
        return customers;
    }
    
    
    public boolean addCustomer(Customer c) {
        try {
            String sql = "INSERT INTO Customer(fullname, firstName, lastName, phone_number, address) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, c.getFullName());
            stm.setString(2, c.getFirstName());
            stm.setString(3, c.getLastName());
            stm.setString(4, c.getPhone_number());
            stm.setString(5, c.getAddress());
            
            
            int kq = stm.executeUpdate();
            
            return kq > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    
    
    public boolean updatCustomer(Customer c) {
        try {
            String sql = "UPDATE customer SET firstname=?, lastname=?, phone_number=? address=? WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, c.getFirstName());
            stm.setString(2, c.getLastName());
            stm.setString(3, c.getPhone_number());
            stm.setString(4, c.getAddress());

            stm.setInt(5, c.getId());
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
