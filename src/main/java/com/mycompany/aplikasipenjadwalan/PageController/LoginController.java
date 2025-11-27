/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;
import com.mycompany.aplikasipenjadwalan.DBController.AksesDBUser;
import com.mycompany.aplikasipenjadwalan.DatabaseConnection;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author raidf
 */
public class LoginController {
    
    @FXML
    private TextField tfUsername;
    
    @FXML
    private PasswordField tfPassword;
    
    @FXML
    private Label lblError;
     
    @FXML
    private void handleLogin() throws IOException {
         User user = new AksesDBUser(DatabaseConnection.connect()).cekLogin(tfUsername.getText(), tfPassword.getText());
         if (user != null) {
            sesiUser.setCurrentUser(user);
            App.setRoot("jadwal");
         } else {
            lblError.setText("Username atau Password salah");
         }
    }
    
    @FXML
    private void goToSignUp() throws IOException {
        App.setRoot("sign_up");
    }
}
