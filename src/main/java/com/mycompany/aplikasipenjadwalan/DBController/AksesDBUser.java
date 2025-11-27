package com.mycompany.aplikasipenjadwalan.DBController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.aplikasipenjadwalan.User;

public class AksesDBUser {
   private Connection connection;

   public AksesDBUser(Connection connection) {
      this.connection = connection;
   }

   public User cekLogin(String nama, String password) {
      String query = "SELECT * FROM user WHERE nama = ? AND password = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setString(1, nama);
         pst.setString(2, password);
         ResultSet rs = pst.executeQuery();
         if (rs.next()) {
            return new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   public boolean register(String username, String password) {
      if(cekAkunByID(username) == null) {
         String query = "INSERT INTO user (username, password) VALUES (?, ?)";
         try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, password);
            pst.executeUpdate();
            return true;
         } catch (SQLException e) {
            e.printStackTrace();
            return false;
         }
      } else{
         return false;
      }
   }

   public User cekAkunByID(String id) {
      String query = "SELECT * FROM user WHERE userId = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setString(1, id);
         ResultSet rs = pst.executeQuery();
         if (rs.next()) {
            return new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }
}
