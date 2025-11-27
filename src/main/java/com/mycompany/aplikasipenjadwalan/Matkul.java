/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

/**
 *
 * @author raidf
 */
public class Matkul {
    private int idMatkul;
    private String namaMatkul;

    public Matkul(int idMatkul, String namaMatkul) {
        this.idMatkul = idMatkul;
        this.namaMatkul = namaMatkul;
    }

    public int getIdMatkul() {
        return idMatkul;
    }
    
    public void setIdMatkul(int idMatkul) {
        this.idMatkul = idMatkul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }
    
    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }
}
