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
public class Recepcionista implements Runnable {

    private Aeropuerto aeropuerto;
    private int aerolinea;

    public Recepcionista(Aeropuerto aeropuerto, int aero) {
        this.aeropuerto = aeropuerto;
        this.aerolinea = aero;
    }

    public void run() {
        while (true) {
            this.aeropuerto.atender(this.aerolinea);
        }
    }

}
