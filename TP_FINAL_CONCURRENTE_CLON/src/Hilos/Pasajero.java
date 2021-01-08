/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Compartido.Aeropuerto;
import Compartido.PuestoAtencion;
import Utiles.Vuelo;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class Pasajero implements Runnable {

    private Aeropuerto aeropuerto;
    private Object[] info;
    private Vuelo vuelo;
    private PuestoAtencion puesto;

    public Pasajero(Aeropuerto aerolinea) {
        this.aeropuerto = aerolinea;
    }

    public void run() {

        info = this.aeropuerto.irAPuestoDeInformes();
        vuelo = (Vuelo) info[0];
        puesto = (PuestoAtencion) info[1];
        System.out.println(Thread.currentThread().getName() + ": Me tocó la aerolinea " + this.vuelo.getAerolinea());
        System.out.println(Thread.currentThread().getName() + ": Mi puesto para la aerolinea " + this.vuelo.getAerolinea() + " es: " + this.puesto);
        this.aeropuerto.entrarAHallPuestoAtencion(this, puesto);
//        this.aeropuerto.dirigirseAPuestoDeAtencion(this.vuelo.getAerolinea());
    }
}
