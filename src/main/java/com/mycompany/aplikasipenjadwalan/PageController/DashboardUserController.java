/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import com.mycompany.aplikasipenjadwalan.sesiUser;
import com.mycompany.aplikasipenjadwalan.DBController.AksesDBUser;

/**
 *
 * @author raidf
 */
public class DashboardUserController {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> colUserId;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colRole;
    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<String> comboRole;
    
    private User selectedUser;
    
    @FXML
    private void initialize() {
        setupTableColumns();
        loadRole();
        loadUsers();
        setupListenerTabel();
    }
    
    private void setupTableColumns() {
        if (colUserId == null || colUsername == null || colRole == null) {
            return;
        }
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }
    
    private void setupListenerTabel() {
        userTable.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<User>() {
                @Override
                public void changed(ObservableValue<? extends User> observable, 
                                  User value1, User value2) {
                    if (value2 != null) {
                        selectedUser = value2;
                        selectKolomTabel(value2);
                    }
                }
            }
        );
    }
    
    private void selectKolomTabel(User user) {
        txtUserId.setText(String.valueOf(user.getUserId()));
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        comboRole.setValue(user.getRole());
    }
    
    private void loadRole() {
        if (comboRole == null) return;
        List<String> roleList = Arrays.asList("admin", "user");
        ObservableList<String> roleItems = FXCollections.observableArrayList(roleList);
        comboRole.setItems(roleItems);
        if(!roleItems.isEmpty()) {
            comboRole.setValue(roleItems.get(0));
        }
    }
    
    private void loadUsers() {
        if (userTable == null) return;
        List<User> userList = new AksesDBUser(DatabaseConnection.connect()).getAllUsers();
        ObservableList<User> userObservableList = FXCollections.observableArrayList(userList);
        userTable.setItems(userObservableList);
    }
    
    @FXML
    private void handleTambahUser() {
        if (txtUserId.getText().isEmpty() || txtUsername.getText().isEmpty() || 
            txtPassword.getText().isEmpty() || comboRole.getValue() == null) {
            failModal("Semua field harus diisi");
            return;
        }
        
        try {
            int userId = Integer.parseInt(txtUserId.getText());
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String role = comboRole.getValue();
            
            User user = new User(userId, username, password, role);
            new AksesDBUser(DatabaseConnection.connect()).addUser(user);
            loadUsers();
            clearInput();
            successModal("Data berhasil disimpan");
        } catch (NumberFormatException e) {
            failModal("ID User harus berupa angka");
        } catch (Exception e) {
            failModal("Gagal menyimpan data: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleUpdateUser() {
        if (selectedUser == null) {
            failModal("Pilih user yang akan diupdate");
            return;
        }
        
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || 
            comboRole.getValue() == null) {
            failModal("Username, Password, dan Role harus diisi");
            return;
        }
        
        try {
            int userId = selectedUser.getUserId();
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String role = comboRole.getValue();
            
            User user = new User(userId, username, password, role);
            new AksesDBUser(DatabaseConnection.connect()).updateUser(user);
            loadUsers();
            clearInput();
            selectedUser = null;
            successModal("Data berhasil diupdate");
        } catch (Exception e) {
            failModal("Gagal mengupdate data: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleDeleteUser() {
        if (selectedUser == null) {
            failModal("Pilih user yang akan dihapus");
            return;
        }
        
        try {
            new AksesDBUser(DatabaseConnection.connect()).deleteUser(selectedUser.getUserId());
            loadUsers();
            clearInput();
            selectedUser = null;
            successModal("Data berhasil dihapus");
        } catch (Exception e) {
            failModal("Gagal menghapus data: " + e.getMessage());
        }
    }
    
    private void clearInput() {
        txtUserId.clear();
        txtUsername.clear();
        txtPassword.clear();
        comboRole.setValue(null);
    }
    
    private void successModal(String pesan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Berhasil");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
    
    private void failModal(String pesan) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gagal");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    @FXML
    private void handleLogout() throws IOException {
        sesiUser.clearSesi();
        App.setRoot("sign_in");
    }
    @FXML
    private void handleHome() throws IOException {
        App.setRoot("dashboard_main");
    }
    @FXML
    private void handleDataDosen() throws IOException {
        App.setRoot("dashboard_dosen");
    }
}
