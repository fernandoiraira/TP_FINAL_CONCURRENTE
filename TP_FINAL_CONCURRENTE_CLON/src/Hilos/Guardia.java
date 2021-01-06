/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Compartido.Aeropuerto;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class Guardia implements Runnable {

    private Aeropuerto aerolinea;

    public Guardia(Aeropuerto aerolinea) {
        this.aerolinea = aerolinea;
    }

    public void run() {
        while (true) {
            this.aerolinea.atender();
        }
    }

}
