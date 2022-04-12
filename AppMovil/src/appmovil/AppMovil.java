/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appmovil;

import Datos.Data;

/**
 *
 * @author orlandocamacho
 */
public class AppMovil {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Data datos = new Data();
        System.out.println(datos.getJson(String.class));
    }
    
}
