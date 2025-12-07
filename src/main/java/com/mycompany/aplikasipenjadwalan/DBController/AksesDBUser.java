package com.mycompany.aplikasipenjadwalan.DBController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            return new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"),
                  rs.getString("role"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   public boolean register(String username, String password) {
      if (!cekAkunByUsername(username)) {
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
      } else {
         return false;
      }
   }

   public boolean tambahUser(String username, String password, String role) {
      if (!cekAkunByUsername(username)) {
         String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
         try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
         } catch (SQLException e) {
            e.printStackTrace();
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean hapusUser(int userId) {
      String query = "DELETE FROM users WHERE userId = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setInt(1, userId);
         int rowsAffected = pst.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public boolean editUser(int userId, String newUsername, String newPassword, String newRole) {
      String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE userId = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setString(1, newUsername);
         pst.setString(2, newPassword);
         pst.setString(3, newRole);
         pst.setInt(4, userId);
         int rowsAffected = pst.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
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

   public List<User> getAllUsers() {
      List<User> userList = new ArrayList<>();
      String query = "SELECT * FROM users ORDER BY userId";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         ResultSet rs = pst.executeQuery();
         while (rs.next()) {
            userList.add(new User(rs.getInt("userId"), rs.getString("username"), 
                rs.getString("password"), rs.getString("role")));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return userList;
   }

   public User getUserByID(int userId) {
      String query = "SELECT * FROM users WHERE userId = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setInt(1, userId);
         ResultSet rs = pst.executeQuery();
         if (rs.next()) {
            return new User(rs.getInt("userId"), rs.getString("username"), 
                rs.getString("password"), rs.getString("role"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   public void addUser(User user) {
      String query = "INSERT INTO users (userId, username, password, role) VALUES (?, ?, ?, ?)";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setInt(1, user.getUserId());
         pst.setString(2, user.getUsername());
         pst.setString(3, user.getPassword());
         pst.setString(4, user.getRole() != null ? user.getRole() : "user");
         pst.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public void updateUser(User user) {
      String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE userId = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setString(1, user.getUsername());
         pst.setString(2, user.getPassword());
         pst.setString(3, user.getRole() != null ? user.getRole() : "user");
         pst.setInt(4, user.getUserId());
         pst.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public void deleteUser(int userId) {
      String query = "DELETE FROM users WHERE userId = ?";
      try (PreparedStatement pst = connection.prepareStatement(query)) {
         pst.setInt(1, userId);
         pst.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}
