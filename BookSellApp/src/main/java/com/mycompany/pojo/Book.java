/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pojo;

import java.math.BigDecimal;

/**
 *
 * @author User
 */
public class Book {
    private int id;
    private String name_book;
    private BigDecimal price;
    private String image;
    private int BookCatalog_id;
    private int Customer_id;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name_book
     */
    public String getName_book() {
        return name_book;
    }

    /**
     * @param name_book the name_book to set
     */
    public void setName_book(String name_book) {
        this.name_book = name_book;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the BookCatalog_id
     */
    public int getBookCatalog_id() {
        return BookCatalog_id;
    }

    /**
     * @param BookCatalog_id the BookCatalog_id to set
     */
    public void setBookCatalog_id(int BookCatalog_id) {
        this.BookCatalog_id = BookCatalog_id;
    }

    /**
     * @return the Customer_id
     */
    public int getCustomer_id() {
        return Customer_id;
    }

    /**
     * @param Customer_id the Customer_id to set
     */
    public void setCustomer_id(int Customer_id) {
        this.Customer_id = Customer_id;
    }
}
