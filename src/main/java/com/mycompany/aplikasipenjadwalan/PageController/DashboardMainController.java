package com.mycompany.aplikasipenjadwalan;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;
import javafx.fxml.FXML;
import com.mycompany.aplikasipenjadwalan.sesiUser;
/**
 *
 * @author raidf
 */

public class DashboardMainController {
    @FXML
    private void initialize() {
    }   
    @FXML
    private void handleDataDosen() throws IOException {  
        App.setRoot("dashboard_dosen");
    }
    @FXML
    private void handleDataUser() throws IOException {
        App.setRoot("dashboard_user");
    }

    @FXML
    private void handleLogout() throws IOException {
        sesiUser.clearSesi();
        App.setRoot("sign_in");
    }
}
