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

   public User cekLogin(String username, String password) {
      String query = "SELECT * FROM users WHERE username = ? AND password = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setString(1, username);
         pst.setString(2, password);
         ResultSet rs = pst.executeQuery();
         if (rs.next()) {
            return new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   public boolean register(String username, String password) {
      if(! cekAkunByUsername(username)) {
         String query = "INSERT INTO users (username, password) VALUES (?, ?)";
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


   public boolean cekAkunByUsername(String username) {
      String query = "SELECT * FROM users WHERE username = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setString(1, username);
         ResultSet rs = pst.executeQuery();
         return rs.next();
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }
}
