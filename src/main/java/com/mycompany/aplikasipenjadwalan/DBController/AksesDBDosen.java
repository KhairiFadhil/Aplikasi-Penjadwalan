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

import com.mycompany.aplikasipenjadwalan.Dosen;

/**
 *
 * @author raidf
 */
public class AksesDBDosen {
    private Connection connection;

    public AksesDBDosen(Connection connection) {
       this.connection = connection;
    }
    
    public Dosen getDosenByID(String id) {
        String query = "SELECT * FROM dosen WHERE idDosen = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Dosen(rs.getInt("idDosen"), rs.getString("namaDosen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addDosen(Dosen dosen) {
        String query = "INSERT INTO dosen (idDosen, namaDosen) VALUES (?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, dosen.getIdDosen());
            pst.setString(2, dosen.getNamaDosen());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Dosen> getAllDosen() {
        List<Dosen> dosenList = new ArrayList<>();
        String query = "SELECT * FROM dosen ORDER BY namaDosen";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dosenList.add(new Dosen(rs.getInt("idDosen"), rs.getString("namaDosen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dosenList;
    }
}
