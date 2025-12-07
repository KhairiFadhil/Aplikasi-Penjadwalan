/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import com.mycompany.aplikasipenjadwalan.sesiUser;
import com.mycompany.aplikasipenjadwalan.DBController.AksesDBDosen;

/**
 *
 * @author raidf
 */
public class DashboardDosenController {
    @FXML
    private TableView<Dosen> dosenTable;
    @FXML
    private TableColumn<Dosen, Integer> colIdDosen;
    @FXML
    private TableColumn<Dosen, String> colNamaDosen;
    @FXML
    private TextField txtIdDosen;
    @FXML
    private TextField txtNamaDosen;
    
    private Dosen selectedDosen;
    
    @FXML
    private void initialize() {
        setupTableColumns();
        loadDosen();
        setupListenerTabel();
    }
    
    private void setupTableColumns() {
        if (colIdDosen == null || colNamaDosen == null) {
            return;
        }
        colIdDosen.setCellValueFactory(new PropertyValueFactory<>("idDosen"));
        colNamaDosen.setCellValueFactory(new PropertyValueFactory<>("namaDosen"));
    }
    
    private void setupListenerTabel() {
        dosenTable.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Dosen>() {
                @Override
                public void changed(ObservableValue<? extends Dosen> observable, 
                                  Dosen value1, Dosen value2) {
                    if (value2 != null) {
                        selectedDosen = value2;
                        selectKolomTabel(value2);
                    }
                }
            }
        );
    }
    
    private void selectKolomTabel(Dosen dosen) {
        txtIdDosen.setText(String.valueOf(dosen.getIdDosen()));
        txtNamaDosen.setText(dosen.getNamaDosen());
    }
    
    private void loadDosen() {
        if (dosenTable == null) return;
        List<Dosen> dosenList = new AksesDBDosen(DatabaseConnection.connect()).getAllDosen();
        ObservableList<Dosen> dosenObservableList = FXCollections.observableArrayList(dosenList);
        dosenTable.setItems(dosenObservableList);
    }
    
    @FXML
    private void handleTambahDosen() {
        if (txtIdDosen.getText().isEmpty() || txtNamaDosen.getText().isEmpty()) {
            failModal("ID Dosen dan Nama Dosen harus diisi");
            return;
        }
        
        try {
            int idDosen = Integer.parseInt(txtIdDosen.getText());
            String namaDosen = txtNamaDosen.getText();
            
            Dosen dosen = new Dosen(idDosen, namaDosen);
            new AksesDBDosen(DatabaseConnection.connect()).addDosen(dosen);
            loadDosen();
            clearInput();
            successModal("Data berhasil disimpan");
        } catch (NumberFormatException e) {
            failModal("ID Dosen harus berupa angka");
        } catch (Exception e) {
            failModal("Gagal menyimpan data: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleUpdateDosen() {
        if (selectedDosen == null) {
            failModal("Pilih dosen yang akan diupdate");
            return;
        }
        
        if (txtNamaDosen.getText().isEmpty()) {
            failModal("Nama Dosen harus diisi");
            return;
        }
        
        try {
            int idDosen = selectedDosen.getIdDosen();
            String namaDosen = txtNamaDosen.getText();
            
            Dosen dosen = new Dosen(idDosen, namaDosen);
            new AksesDBDosen(DatabaseConnection.connect()).updateDosen(dosen);
            loadDosen();
            clearInput();
            selectedDosen = null;
            successModal("Data berhasil diupdate");
        } catch (Exception e) {
            failModal("Gagal mengupdate data: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleDeleteDosen() {
        if (selectedDosen == null) {
            failModal("Pilih dosen yang akan dihapus");
            return;
        }
        
        try {
            new AksesDBDosen(DatabaseConnection.connect()).deleteDosen(selectedDosen.getIdDosen());
            loadDosen();
            clearInput();
            selectedDosen = null;
            successModal("Data berhasil dihapus");
        } catch (Exception e) {
            failModal("Gagal menghapus data: " + e.getMessage());
        }
    }
    
    private void clearInput() {
        txtIdDosen.clear();
        txtNamaDosen.clear();
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
    private void handleDataDosen() throws IOException {
        App.setRoot("dashboard_dosen");
    }
    @FXML
    private void handleLogout() throws IOException {
        sesiUser.clearSesi();
        App.setRoot("sign_in");
    }
    @FXML
    private void handleDataUser() throws IOException {
        App.setRoot("dashboard_user");
    }
    @FXML
    private void handleHome() throws IOException {
        App.setRoot("dashboard_main");
    }
}
