package com.mycompany.booksellapp;

import booksellapp.Utils;
import com.mycompany.pojo.Book;
import com.mycompany.pojo.Book_catalog;
import com.mycompany.service.BookService;
import com.mycompany.service.Book_catalogService;
import com.mycompany.service.JdbcUtils;
import com.mycompany.service.LoginService;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PrimaryController implements Initializable{
    @FXML private ComboBox<Book_catalog> cbCates;
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TableView<Book> tbBooks;
    @FXML private TextField txtKeyWord;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("primary");
    }
    @FXML
    void ChangeToLogin(ActionEvent event) throws IOException{
        Parent registerView = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        Scene registerViewScene = new Scene(registerView);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(registerViewScene);
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Book_catalogService s = new Book_catalogService();
        try {
            cbCates.setItems(FXCollections.observableList(s.getBookcatalog()));
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadColumns();
        try {
            loadBooks("");
        } catch (SQLException ex) {
            System.out.println("ERROR" + ex);
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtKeyWord.textProperty().addListener((obj) -> {
            try {
                loadBooks(txtKeyWord.getText());
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void loadColumns() {
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Name");
        colName.setCellValueFactory(new PropertyValueFactory("name_book"));
        
        TableColumn colPrice = new TableColumn("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        TableColumn colAuthor = new TableColumn("Author");
        colPrice.setCellValueFactory(new PropertyValueFactory("author"));
        
        TableColumn colAction = new TableColumn("ACTIONS");
        colAction.setCellFactory(obj -> {
            Button btn = new Button("Xóa");
            btn.setOnAction(evt -> {
                Utils.getBox("Bạn có chắc chắn xóa không?", Alert.AlertType.CONFIRMATION)
                     .showAndWait().ifPresent(b -> {
                         if (b == ButtonType.OK) {
                             Button bt = (Button) evt.getSource();
                             TableCell cell = (TableCell) bt.getParent();
                             Book q = (Book)cell.getTableRow().getItem();
                             
                             Connection conn;
                             try {
                                 conn = JdbcUtils.getConn();
                                 BookService s = new BookService(conn);
                                 
                                 if (s.deleteBook(q.getId())) {
                                     Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                                     loadBooks("");
                                 } else
                                     Utils.getBox("FAILED", Alert.AlertType.ERROR).show();
                                 
                                 conn.close();
                             } catch (SQLException ex) {
                                 Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                             }
                         }
                    });
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });
        
        this.tbBooks.getColumns().addAll(colId, colName, colPrice, colAuthor,colAction);
    }
    
    private void loadBooks(String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        
        BookService s = new BookService(conn);
        
        tbBooks.setItems(FXCollections.observableArrayList(s.getBooks(kw)));
        
        conn.close();
    }
    
     public void addHandler(ActionEvent evt) {
        try {
            Connection conn = JdbcUtils.getConn();
            BookService p = new BookService(conn);
            
            Book b = new Book();
            b.setName_book(txtName.getText());
            b.setPrice(new BigDecimal(txtPrice.getText()));
            b.setBookCatalog_id(cbCates.getSelectionModel().getSelectedItem().getId());
            
            
            Alert a =new Alert(Alert.AlertType.INFORMATION);
            if (p.addBook(b) == true) 
                a.setContentText("SUCCESSFUL");
            else
                a.setContentText("FAILED");
            
            a.show();
            
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
     
     public void updateHandler(ActionEvent evt) throws SQLException {
        Book p = this.tbBooks.getSelectionModel().getSelectedItem();
        p.setName_book(txtName.getText());
        p.setPrice(new BigDecimal(txtPrice.getText()));
        p.setBookCatalog_id(this.cbCates.getSelectionModel().getSelectedItem().getId());
        
        Connection conn = JdbcUtils.getConn();
        BookService s = new BookService(conn);
        if (s.updateBook(p) == true) {
            Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
            loadBooks("");
        } else
            Utils.getBox("FAILED", Alert.AlertType.ERROR).show();
        conn.close();
    }
}
    