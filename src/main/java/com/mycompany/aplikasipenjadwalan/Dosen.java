/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

/**
 *
 * @author raidf
 */
public class Dosen {
    private int idDosen;
    private String namaDosen;

    public Dosen(int idDosen, String namaDosen) {
        this.idDosen = idDosen;
        this.namaDosen = namaDosen;
    }

    public int getIdDosen() {
        return idDosen;
    }
    
    public void setIdDosen(int idDosen) {
        this.idDosen = idDosen;
    }

    public String getNamaDosen() {
        return namaDosen;
    }
    
    public void setNamaDosen(String namaDosen) {
        this.namaDosen = namaDosen;
    }
}
