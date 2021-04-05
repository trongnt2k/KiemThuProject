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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        ////CHẠY THỬ RỒI KIỂM TRA LẠI XEM SQL CHẠY ĐÚNG KO. WHERE CHỈ CÓ NAME, TÌM AUTHOR ĐC KO?
        String sql ="SELECT * FROM book WHERE name_book like concat('%', ?, '%')";
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
            b.setAuthor(rs.getString("author"));
            
            books.add(b);
        }
        return books;
    }
    
    
     public boolean addBook(Book b) {
        try {
            String sql = "INSERT INTO Book(name_book, price, BookCatalog_id, author) VALUES(?, ?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, b.getName_book());
            stm.setBigDecimal(2, b.getPrice());
            stm.setInt(3, b.getBookCatalog_id());
            stm.setString(4, b.getAuthor());
            
            
            int kq = stm.executeUpdate();
            
            return kq > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean deleteBook(int bookId) {
       
        try {
            String sql = "DELETE FROM book WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, bookId);
            
            int row = stm.executeUpdate();
            
            return row > 0;
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean updateBook(Book b) {
        try {
            String sql = "UPDATE book SET name_book=?, price=?, BookCatalog_id=? author=? WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, b.getName_book());
            stm.setBigDecimal(2, b.getPrice());
            stm.setInt(3, b.getBookCatalog_id());
            stm.setString(4, b.getAuthor());

            stm.setInt(5, b.getId());
            
            int rows = stm.executeUpdate();
            
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
   
}


