/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasipenjadwalan;

/**
 *
 * @author raidf
 */
public class sesiUser {
    private static User currentUser;
    
    public static void setCurrentUser(User user){
        currentUser = user;
    }
    
    public static User getCurrentUser(){
        return currentUser;
    }
    
    public static void clearSesi(){
        currentUser = null;
    }
    
}
