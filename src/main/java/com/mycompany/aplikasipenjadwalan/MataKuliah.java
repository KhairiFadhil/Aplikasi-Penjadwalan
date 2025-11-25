/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

/**
 *
 * @author raidf
 */
public class MataKuliah {
    private String kodeMatkul;
    private String namaMatkul;
     
    public MataKuliah(String idMatkul, String namaMatkul){
        this.kodeMatkul = idMatkul;
        this.namaMatkul = namaMatkul;
    }
       public String getKodeMatkul() {
        return kodeMatkul;
    }

    public void setIdMatkul(String idMatkul) {
        this.kodeMatkul = idMatkul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    } 
    
}
