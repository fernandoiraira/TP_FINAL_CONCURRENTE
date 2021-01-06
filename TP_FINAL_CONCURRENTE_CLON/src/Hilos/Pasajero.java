/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Compartido.Aeropuerto;
import Utiles.Vuelo;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class Pasajero implements Runnable {

    private Aeropuerto aeropuerto;
    private Vuelo vuelo;

    public Pasajero(Aeropuerto aerolinea) {
        this.aeropuerto = aerolinea;
    }

    public void run() {

        vuelo = this.aeropuerto.irAPuestoDeInformes();
        System.out.println(Thread.currentThread().getName() + ": Me tocó la aerolinea " + this.vuelo.getAerolinea());
        this.aeropuerto.dirigirseAPuestoDeAtencion(this.vuelo.getAerolinea());
    }
}
