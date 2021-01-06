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
    private Object[] info;
    private Vuelo vuelo;
    private int turno;

    public Pasajero(Aeropuerto aerolinea) {
        this.aeropuerto = aerolinea;
    }

    public void run() {

        info = this.aeropuerto.irAPuestoDeInformes();
        vuelo = (Vuelo) info[0];
        turno = (int) info[1];
        System.out.println(Thread.currentThread().getName() + ": Me tocó la aerolinea " + this.vuelo.getAerolinea());
        System.out.println(Thread.currentThread().getName() + ": Mi turno para la aerolinea " + this.vuelo.getAerolinea() + " es: " + this.turno);
//        this.aeropuerto.dirigirseAPuestoDeAtencion(this.vuelo.getAerolinea());
    }
}
