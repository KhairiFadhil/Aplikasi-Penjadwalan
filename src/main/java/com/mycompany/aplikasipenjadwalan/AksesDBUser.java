package com.aplikasipenjadwalan.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AksesDBUser {
   private Connection connection;

   public AksesDBUser(Connection connection) {
      this.connection = connection;
   }

   public boolean cekLogin(String nama, String password) {
      String query = "SELECT * FROM user WHERE nama = ? AND password = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setString(1, nama);
         pst.setString(2, password);
         ResultSet rs = pst.executeQuery();
         return rs.next();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }

   public boolean cekAkunByID(String id) {
      String query = "SELECT * FROM user WHERE userId = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setString(1, id);
         ResultSet rs = pst.executeQuery();
         return rs.next();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }
}
