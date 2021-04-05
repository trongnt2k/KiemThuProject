/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.pojo.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Admin
 */
public class BookTester {
    private static Connection conn;
    
    @BeforeAll
    public static void setUpClass() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void tearDownClass() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    public void testWithKeyWord() {
        try {
            BookService s = new BookService(conn);
            List<Book> products = s.getBooks("iphone");
            
            products.forEach(p -> {
                Assertions.assertTrue(p.getName_book().toLowerCase().contains("sách"));
            });
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @Tag("critical")
    public void testWithUnknownKeyWord() {
        try {
            BookService s = new BookService(conn);
            List<Book> products = s.getBooks("jhwgejhqsagdjhsgdjhsa232432");
            
            Assertions.assertEquals(0, products.size());
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testException() {
        Assertions.assertThrows(SQLDataException.class, () -> {
            BookService s = new BookService(conn);
            List<Book> books = s.getBooks(null);
        });
    }
    
    @Test
    public void testTimeout() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            BookService s = new BookService(conn);
            List<Book> books = s.getBooks("");
        });
    }
    
    @Test
    @DisplayName("Kiem thu them sp voi name = null")
    @Tag("critical")
    public void tesAddBookWithNameNull() throws SQLException {
        BookService s = new BookService(conn);
        Book b = new Book();
        b.setName_book(null);
        b.setPrice(new BigDecimal(999));
        b.setBookCatalog_id(1);
        b.setCustomer_id(1);
        
        Assertions.assertFalse(s.addBook(b));
    }
    
    @Test
    @Tag("critical")
    public void tesAddBookWithInvalidCate() throws SQLException {
        BookService s = new BookService(conn);
        Book b = new Book();
        b.setName_book("TEST BOOK");
        b.setPrice(new BigDecimal(999));
        b.setBookCatalog_id(9999);
        b.setCustomer_id(1);
        
        Assertions.assertFalse(s.addBook(b));
    }
    @Test
    @Tag("critical")
    public void tesAddBookWithInvalidCusId() throws SQLException {
        BookService s = new BookService(conn);
        Book b = new Book();
        b.setName_book("TEST BOOK");
        b.setPrice(new BigDecimal(999));
        b.setBookCatalog_id(9999);
        b.setCustomer_id(9999);
        
        Assertions.assertFalse(s.addBook(b));
    }
    
    @Test
    @Tag("critical")
    public void tesAddBook() throws SQLException {
        BookService s = new BookService(conn);
        Book b = new Book();
        b.setName_book("TEST BOOK");
        b.setPrice(new BigDecimal(999));
        b.setBookCatalog_id(1);
        b.setCustomer_id(1);
        
        Assertions.assertTrue(s.addBook(b));
        
        //
    }
    
    @ParameterizedTest
    @CsvSource({"P1,999,1,true", "P2,999,99,false", ",99,1,false"})
    public void tesAddProductParams(String name, BigDecimal price, int cateId, int CusId, boolean expected) throws SQLException {
        BookService s = new BookService(conn);
        Book b = new Book();
        b.setName_book(name);
        b.setPrice(price);
        b.setBookCatalog_id(cateId);
        b.setCustomer_id(CusId);
        
        Assertions.assertEquals(expected, s.addBook(b));
//        Assertions.assertTrue(s.addProduct(p));
        
        //
    }
}
