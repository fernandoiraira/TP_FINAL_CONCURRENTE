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
public class Pasajero implements Runnable {

    private Aeropuerto aerolinea;
    private int numAerolinea;

    public Pasajero(Aeropuerto aerolinea) {
        this.aerolinea = aerolinea;
    }

    public void run() {
        this.numAerolinea = this.aerolinea.ingresarAeropuerto();
        System.out.println(Thread.currentThread().getName() + ": Me tocó la aerolinea " + this.numAerolinea);
        this.aerolinea.dirigirseAPuestoDeAtencion(this.numAerolinea);
    }
}
