/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

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
        System.out.println("Username : " + tfUsername);
        System.out.println("Password : " + tfPassword);
        System.out.println("Confirm Password : " + tfConfirm);
    }
    
    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("sign_in");
    }
}
