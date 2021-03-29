/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.pojo.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class BookService {
    private Connection conn;
    
    public BookService (Connection conn){
        this.conn = conn;
    }
    
    public List<Book> getBooks(String kw) throws SQLException{
        if (kw == null)
            throw new SQLDataException();
        
        String sql ="SELECT * FROM book WHERE name like concat('%', ?, '%')";
        PreparedStatement stm =this.conn.prepareStatement(sql);
        stm.setString(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<Book> books = new ArrayList<>();
        while(rs.next()){
            Book b = new Book();
            b.setId(rs.getInt("id"));
            b.setName_book(rs.getString("name_book"));
            b.setPrice(rs.getBigDecimal("price"));
            b.setImage(rs.getString("image"));
            b.setBookCatalog_id(rs.getInt("BookCatalog_id"));
            b.setCustomer_id(rs.getInt("Customer_id"));
            
            books.add(b);
        }
        return books;
    }
    public boolean addBook(Book p) throws SQLException {
        String sql = "INSERT INTO book(name_book, price, BookCatalog_id, Customer_id) VALUES(?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, p.getName_book());
        stm.setBigDecimal(2, p.getPrice());
        stm.setInt(3, p.getBookCatalog_id());
        stm.setInt(4, p.getCustomer_id());
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
}
