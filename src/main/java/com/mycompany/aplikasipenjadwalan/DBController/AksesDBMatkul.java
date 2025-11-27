/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan.DBController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.aplikasipenjadwalan.Matkul;

/**
 *
 * @author raidf
 */
public class AksesDBMatkul {
    private Connection connection;

    public AksesDBMatkul(Connection connection) {
        this.connection = connection;
    }

    public Matkul getMatkulByID(String id) {
        String query = "SELECT * FROM matkul WHERE idMatkul = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Matkul(rs.getInt("idMatkul"), rs.getString("namaMatkul"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Matkul> getAllMatkul() {
        List<Matkul> matkulList = new ArrayList<>();
        String query = "SELECT * FROM matkul ORDER BY namaMatkul";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                matkulList.add(new Matkul(rs.getInt("idMatkul"), rs.getString("namaMatkul")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matkulList;
    }

    public void addMatkul(Matkul matkul) {
        String query = "INSERT INTO matkul (idMatkul, namaMatkul) VALUES (?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, matkul.getIdMatkul());
            pst.setString(2, matkul.getNamaMatkul());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
