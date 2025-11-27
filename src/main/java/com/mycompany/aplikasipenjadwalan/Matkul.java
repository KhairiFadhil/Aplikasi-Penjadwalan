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
    private int kodeMatkul;
    private String namaMatkul;

    public Matkul(int kodeMatkul, String namaMatkul) {
        this.kodeMatkul = kodeMatkul;
        this.namaMatkul = namaMatkul;
    }

    public int getKodeMatkul() {
        return kodeMatkul;
    }
    
    public void setKodeMatkul(int kodeMatkul) {
        this.kodeMatkul = kodeMatkul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }
    
    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }
}
