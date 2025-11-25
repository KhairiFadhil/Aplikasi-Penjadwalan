/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class DatabaseConnection {
    private static Connection conn;
    private static final String URL = "jdbc:mysql://localhost:3306/penjadwalan?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static Connection connect() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/penjadwalan", USER, PASSWORD);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Berhasil Connect!");
                alert.setHeaderText(null);
                alert.setContentText("Berhasil Connect ke Database");
                alert.show();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Koneksi Gagal");
                alert.setHeaderText("Database Belum Aktif");
                alert.setContentText("Database tidak ditemukan \n" +  e.getMessage());
                alert.show();
                e.printStackTrace();
            }
        }
        return conn;
    }
}
