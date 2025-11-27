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
import java.time.LocalTime;
import java.time.LocalDate;
import java.io.IOException;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 *
 * @author raidf
 */
public class JadwalController {
    @FXML
    private Label lblTotalJadwal;
    @FXML
    private Label lblTotalSudahHadir;
    @FXML
    private Label lblTotalBelumHadir;
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
    private TableView<Jadwal> jadwalTable;
    @FXML
    private TableColumn<Jadwal, String> colKodeMatkul;
    @FXML
    private TableColumn<Jadwal, String> colNamaDosen;
    @FXML
    private TableColumn<Jadwal, String> colRuang;
    @FXML
    private TableColumn<Jadwal, String> colTanggal;
    @FXML
    private TableColumn<Jadwal, String> colJamMulai;
    @FXML
    private TableColumn<Jadwal, String> colJamSelesai;
    @FXML
    private TableColumn<Jadwal, String> colDeskripsi;
    
    private Jadwal selectedJadwal;
    
    @FXML
    private void initialize() {
        setupTableColumns();
        loadDosen();
        loadMatkul();
        getJadwal();
        loadStatus();
        setupListenerTabel();
        loadInfoSingkat();
    }
    
    private void setupListenerTabel() {
        // event listener (buat kalau ngeselec4t jadi tau programmnya kalau gw select rownya)
        jadwalTable.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Jadwal>() {
                @Override
                public void changed(ObservableValue<? extends Jadwal> observable, 
                                  Jadwal value1, Jadwal value2) {
                    if (value2 != null) {
                        selectedJadwal = value2;
                        selectKolomTabel(value2);
                    }
                }
            }
        );
    }
    
    //fungsi yg dijalanin kalau ngeselect
    private void selectKolomTabel(Jadwal jadwal) {
        Matkul matkul = new AksesDBMatkul(DatabaseConnection.connect()).getMatkulByID(jadwal.getKodeMatkul());
        if (matkul != null) {
            comboMatkul.setValue(matkul.getNamaMatkul());
        }
        Dosen dosen = new AksesDBDosen(DatabaseConnection.connect()).getDosenByID(jadwal.getIdDosen());
        if (dosen != null) {
            comboDosen.setValue(dosen.getNamaDosen());
        }

        datePicker.setValue(jadwal.getTanggal());
        LocalTime jamMulai = jadwal.getJamMulai();
        if (jamMulai != null) {
            txtStartHour.setText(String.format("%02d", jamMulai.getHour()));
            txtStartMinute.setText(String.format("%02d", jamMulai.getMinute()));
        }

        LocalTime jamSelesai = jadwal.getJamSelesai();
        if (jamMulai != null && jamSelesai != null) {
            long durationMinutes = java.time.Duration.between(jamMulai, jamSelesai).toMinutes();
            txtDuration.setText(String.valueOf(durationMinutes));
        }

        txtLocation.setText(jadwal.getRuang());
        comboStatus.setValue(jadwal.getDeskripsi());
    }

    private int totalSudahHadir(){
        List<Jadwal> jadwals = new AksesDBJadwal(DatabaseConnection.connect()).getJadwalByUserID(sesiUser.getCurrentUser().getUserId());
        int totalSudahHadir = 0;
        for (Jadwal jadwal : jadwals) {
            if (jadwal.getDeskripsi().equals("Sudah Hadir")) {
                totalSudahHadir++;
            }
        }
        return totalSudahHadir;
    }

    private int totalBelumHadir(){
        List<Jadwal> jadwals = new AksesDBJadwal(DatabaseConnection.connect()).getJadwalByUserID(sesiUser.getCurrentUser().getUserId());
        int totalBelumHadir = 0;
        for (Jadwal jadwal : jadwals) {
            if (jadwal.getDeskripsi().equals("Belum Hadir")) {
                totalBelumHadir++;
            }
        }
        return totalBelumHadir;
    }

    private String lokasiSelanjutnya(){
        List<Jadwal> jadwals = new AksesDBJadwal(DatabaseConnection.connect()).getJadwalByUserID(sesiUser.getCurrentUser().getUserId());
        for (Jadwal jadwal : jadwals) {
            if (jadwal.getDeskripsi().equals("Belum Hadir")) {
                return jadwal.getRuang();
            }
        }
        return "Kosong";
    }

    private int totalJadwal(){
        List<Jadwal> jadwals = new AksesDBJadwal(DatabaseConnection.connect()).getJadwalByUserID(sesiUser.getCurrentUser().getUserId());
        return jadwals.size();
    }
   
    private void loadInfoSingkat(){
        lblTotalJadwal.setText(String.valueOf(totalJadwal()));
        lblTotalSudahHadir.setText(String.valueOf(totalSudahHadir()));
        lblTotalBelumHadir.setText(lokasiSelanjutnya());
    }
    private void loadStatus(){
        if (comboStatus == null) return;
        List<String> statusList = Arrays.asList("Sudah Hadir", "Belum Hadir");
        ObservableList<String> statusItems = FXCollections.observableArrayList(statusList);
        comboStatus.setItems(statusItems);
        if(!statusItems.isEmpty()) {
            comboStatus.setValue(statusItems.get(0));
        }
    }
    
    private void setupTableColumns() {
        if (colKodeMatkul == null || colNamaDosen == null || colRuang == null || 
            colTanggal == null || colJamMulai == null || colJamSelesai == null || 
            colDeskripsi == null) {
            return;
        }
        
        colKodeMatkul.setCellValueFactory(new PropertyValueFactory<>("kodeMatkul"));
        colRuang.setCellValueFactory(new PropertyValueFactory<>("ruang"));
        colDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));

        colTanggal.setCellValueFactory(cellData -> {
            LocalDate tanggal = cellData.getValue().getTanggal();
            return new javafx.beans.property.SimpleStringProperty(
                tanggal != null ? tanggal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""
            );
        });

        colJamMulai.setCellValueFactory(cellData -> {
            LocalTime jamMulai = cellData.getValue().getJamMulai();
            return new javafx.beans.property.SimpleStringProperty(
                jamMulai != null ? jamMulai.format(DateTimeFormatter.ofPattern("HH:mm")) : ""
            );
        });

        colJamSelesai.setCellValueFactory(cellData -> {
            LocalTime jamSelesai = cellData.getValue().getJamSelesai();
            return new javafx.beans.property.SimpleStringProperty(
                jamSelesai != null ? jamSelesai.format(DateTimeFormatter.ofPattern("HH:mm")) : ""
            );
        });

        colNamaDosen.setCellValueFactory(cellData -> {
            String idDosen = cellData.getValue().getIdDosen();
            if (idDosen != null && !idDosen.isEmpty()) {
                try {
                    Dosen dosen = new AksesDBDosen(DatabaseConnection.connect()).getDosenByID(idDosen);
                    return new javafx.beans.property.SimpleStringProperty(
                        dosen != null ? dosen.getNamaDosen() : idDosen
                    );
                } catch (Exception e) {
                    return new javafx.beans.property.SimpleStringProperty(idDosen);
                }
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
    }
    
    private void loadDosen() {
        if (comboDosen == null) return;
        List<Dosen> dosenList = new AksesDBDosen(DatabaseConnection.connect()).getAllDosen();
        ObservableList<String> namaDosen = FXCollections.observableArrayList();
        for (Dosen dosen : dosenList) {
            namaDosen.add(dosen.getNamaDosen());
        }
        comboDosen.setItems(namaDosen);
        if(!namaDosen.isEmpty()) {
            comboDosen.setValue(namaDosen.get(0));
        }
    }

    private void loadMatkul() {
        if (comboMatkul == null) return;
        List<Matkul> matkulList = new AksesDBMatkul(DatabaseConnection.connect()).getAllMatkul();
        ObservableList<String> namaMatkul = FXCollections.observableArrayList();
        for (Matkul matkul : matkulList) {
            namaMatkul.add(matkul.getNamaMatkul());
        }
        comboMatkul.setItems(namaMatkul);
        if(!namaMatkul.isEmpty()) {
            comboMatkul.setValue(namaMatkul.get(0));
        }
    }

    @FXML
    private void handleTambahJadwal() {
        if (comboMatkul.getValue() == null || comboDosen.getValue() == null || 
            datePicker.getValue() == null || txtStartHour.getText().isEmpty() || 
            txtStartMinute.getText().isEmpty() || txtDuration.getText().isEmpty() ||
            txtLocation.getText().isEmpty()) {
            return;
        }

        Matkul matkul = new AksesDBMatkul(DatabaseConnection.connect()).getMatkulByNama(comboMatkul.getValue());
        if (matkul == null) {
            return;
        }
        String kodeMatkul = String.valueOf(matkul.getKodeMatkul());
        Dosen dosen = new AksesDBDosen(DatabaseConnection.connect()).getDosenByNama(comboDosen.getValue());
        if (dosen == null) {
            return;
        }
        String idDosen = String.valueOf(dosen.getIdDosen());
        int hour = Integer.parseInt(txtStartHour.getText());
        int minute = Integer.parseInt(txtStartMinute.getText());
        LocalTime jamMulai = LocalTime.of(hour, minute);
        int duration = Integer.parseInt(txtDuration.getText());
        LocalTime jamSelesai = jamMulai.plusMinutes(duration);
        if (jamMulai.isAfter(jamSelesai)) {
            failModal("Jam mulai tidak boleh lebih besar dari jam selesai");
            return;
        }
        Jadwal jadwal = new Jadwal(0, sesiUser.getCurrentUser().getUserId(), 
            kodeMatkul, idDosen, txtLocation.getText(), datePicker.getValue(), 
            jamMulai, jamSelesai, comboStatus.getValue() != null ? comboStatus.getValue() : "");
        new AksesDBJadwal(DatabaseConnection.connect()).tambahJadwal(jadwal);
        getJadwal();
        loadInfoSingkat();
        clearInput();
        successModal("Data berhasil disimpan");
    }
    
    @FXML
    private void handleUpdateJadwal() {
        if (selectedJadwal == null) {
            return;
        }
        
        if (comboMatkul.getValue() == null || comboDosen.getValue() == null || 
            datePicker.getValue() == null || txtStartHour.getText().isEmpty() || 
            txtStartMinute.getText().isEmpty() || txtDuration.getText().isEmpty() ||
            txtLocation.getText().isEmpty()) {
            return;
        }

        Matkul matkul = new AksesDBMatkul(DatabaseConnection.connect()).getMatkulByNama(comboMatkul.getValue());
        if (matkul == null) {
            failModal("Matkul tidak ditemukan");
            return;
        }
        String kodeMatkul = String.valueOf(matkul.getKodeMatkul());
        Dosen dosen = new AksesDBDosen(DatabaseConnection.connect()).getDosenByNama(comboDosen.getValue());
        if (dosen == null) {
            failModal("Dosen tidak ditemukan");
            return;
        }
        String idDosen = String.valueOf(dosen.getIdDosen());
        int hour = Integer.parseInt(txtStartHour.getText());
        int minute = Integer.parseInt(txtStartMinute.getText());
        LocalTime jamMulai = LocalTime.of(hour, minute);
        int duration = Integer.parseInt(txtDuration.getText());
        LocalTime jamSelesai = jamMulai.plusMinutes(duration);
        if (jamMulai.isAfter(jamSelesai)) {
            failModal("Jam mulai tidak boleh lebih besar dari jam selesai");
            return;
        }
   
        Jadwal jadwal = new Jadwal(selectedJadwal.getId(), sesiUser.getCurrentUser().getUserId(), 
            kodeMatkul, idDosen, txtLocation.getText(), datePicker.getValue(), 
            jamMulai, jamSelesai, comboStatus.getValue() != null ? comboStatus.getValue() : "");
        
        new AksesDBJadwal(DatabaseConnection.connect()).updateJadwal(jadwal);
        getJadwal();
        loadInfoSingkat();
        clearInput();
        selectedJadwal = null;
        successModal("Data berhasil diupdate");
    }
    
    @FXML
    private void handleDeleteJadwal() {
        if (selectedJadwal == null) {
            return;
        }
        
        new AksesDBJadwal(DatabaseConnection.connect()).deleteJadwal(selectedJadwal.getId());
        getJadwal();
        loadInfoSingkat();
        clearInput();
        selectedJadwal = null;
        successModal("Data berhasil dihapus");
    }

    private void getJadwal(){
        if (jadwalTable == null || sesiUser.getCurrentUser() == null) return;
        List<Jadwal> jadwals = new AksesDBJadwal(DatabaseConnection.connect()).getJadwalByUserID(sesiUser.getCurrentUser().getUserId());
        if (jadwals.isEmpty()) {
            failModal("Tidak ada jadwal");
            return;
        }
        ObservableList<Jadwal> jadwalList = FXCollections.observableArrayList(jadwals);
        jadwalTable.setItems(jadwalList);
    }

    @FXML
    private void handleLogout() throws IOException {
        sesiUser.clearSesi();
        App.setRoot("sign_in");
    }

    private void successModal(String pesan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Berhasil");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
    
    private void failModal(String pesan){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gagal");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    
    private void clearInput(){
     comboDosen.setValue(null);
     datePicker.setValue(null);
     txtStartHour.clear();
     txtStartMinute.clear();
     txtDuration.clear();
     txtLocation.clear();
     comboMatkul.setValue(null);
     comboStatus.setValue(null);
    }
}
