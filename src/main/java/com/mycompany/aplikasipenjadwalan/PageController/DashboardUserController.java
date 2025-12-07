/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

import java.io.IOException;
import javafx.fxml.FXML;
import com.mycompany.aplikasipenjadwalan.sesiUser;

/**
 *
 * @author raidf
 */
public class DashboardUserController {
    
    @FXML
    private void initialize() {
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
