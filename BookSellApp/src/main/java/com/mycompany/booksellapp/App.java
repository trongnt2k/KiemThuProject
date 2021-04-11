package com.mycompany.booksellapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("register"), 840, 680);
        
        stage.setScene(scene);
        stage.show();
        
        
//        MenuBar menuBar = new MenuBar();
//        
//        
//        Menu fileMenu = new Menu("File");
//        Menu editMenu = new Menu("Edit");
//        Menu helpMenu = new Menu("Help");
//        
//        
//        MenuItem newItem = new MenuItem("New");
//        MenuItem openFileItem = new MenuItem("Open File");
//        MenuItem editItem = new MenuItem("Edit");
//        
//        
//        fileMenu.getItems().addAll(newItem, openFileItem, editItem);
//        
//        
//        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
//        
//        
//        BorderPane root = new BorderPane();
//        root.setTop(menuBar);
//       
//        
//        stage.setTitle("Quan ly cua hang sach");
//        stage.setScene(new Scene(root, 350,200));
//        stage.show();
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}