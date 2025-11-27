/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

import com.mycompany.aplikasipenjadwalan.DBController.AksesDBUser;
import com.mycompany.aplikasipenjadwalan.DatabaseConnection;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

/**
 *
 * @author raidf
 */
public class SignUpController {
    
    @FXML
    private TextField tfUsername;
    
    @FXML
    private PasswordField tfPassword;
    
    @FXML
    private PasswordField tfConfirm;
    
    @FXML
    private Label lblError;
    
    @FXML
    private void handleSignUp() throws IOException {
      boolean success = new AksesDBUser(DatabaseConnection.connect()).register(tfUsername.getText(), tfPassword.getText());
      if (success) {
         lblError.setText("Berhasil mendaftar akun");
         App.setRoot("sign_in");
      } else {
         lblError.setText("Gagal mendaftar akun");
      }
    }
    
    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("sign_in");
    }
}
