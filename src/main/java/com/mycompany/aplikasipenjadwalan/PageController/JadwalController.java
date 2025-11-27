/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

import com.mycompany.aplikasipenjadwalan.DBController.AksesDBJadwal;
import com.mycompany.aplikasipenjadwalan.DBController.AksesDBDosen;
import com.mycompany.aplikasipenjadwalan.DBController.AksesDBMatkul;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author raidf
 */
public class JadwalController {
    @FXML
    private TextField txtStudentName;
    @FXML
    private TextField txtStudentID;
    @FXML
    private ComboBox<String> comboDosen;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtStartHour;
    @FXML
    private TextField txtStartMinute;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtLocation;
    @FXML
    private ComboBox<String> comboMatkul;
    @FXML
    private ComboBox<String> comboStatus;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Hyperlink linkLoginAdmin;
    
    @FXML
    private void initialize() {
        loadDosen();
        loadMatkul();
    }
    
    private void loadDosen() {
        List<Dosen> dosenList = new AksesDBDosen(DatabaseConnection.connect()).getAllDosen();
        ObservableList<String> namaDosen = FXCollections.observableArrayList();
        for (Dosen dosen : dosenList) {
            namaDosen.add(dosen.getNamaDosen());
        }
        comboDosen.setItems(namaDosen);
    }

    private void loadMatkul() {
        List<Matkul> matkulList = new AksesDBMatkul(DatabaseConnection.connect()).getAllMatkul();
        ObservableList<String> namaMatkul = FXCollections.observableArrayList();
        for (Matkul matkul : matkulList) {
            namaMatkul.add(matkul.getNamaMatkul());
        }
        comboMatkul.setItems(namaMatkul);
    }

    @FXML
    private void handleTambahJadwal() {
        Jadwal jadwal = new Jadwal(0, sesiUser.getCurrentUser().getUserId(), txtKodeMatkul.getText(), txtIdDosen.getText(), txtRuang.getText(), datePicker.getValue(), txtStartHour.getText(), txtStartMinute.getText(), txtDuration.getText(), txtLocation.getText(), comboStatus.getValue());
        new AksesDBJadwal(DatabaseConnection.connect()).tambahJadwal(jadwal);
        lblError.setText("Jadwal berhasil ditambahkan");
    }

    private void getJadwal(){
        List<Jadwal> jadwals = new AksesDBJadwal(DatabaseConnection.connect()).getJadwalByUserID(sesiUser.getCurrentUser().getUserId());
       
    }
    
    private void clearInput(){
     txtStudentName.clear();
     txtStudentID.clear();
     comboAdvisor.setValue(null);
     datePicker.setValue(null);
     txtStartHour.clear();
     txtStartMinute.clear();
     txtDuration.clear();
     txtLocation.clear();
     comboMatkul.setValue(null);
     comboStatus.setValue(null);
    }
}
