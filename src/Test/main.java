/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Compartido.Aeropuerto;
import Hilos.Pasajero;
import Hilos.Reloj;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class main {

    public static void main(String[] args) {
        int cantPasajeros = 10;
        int cantAerolineas = 10;
        int capPuestosAtencion = 1;
        int cantSegundos = 2;

        int capMaxTren = 5;

        Aeropuerto aerolinea = new Aeropuerto(cantAerolineas, capMaxTren, capPuestosAtencion);

        Reloj r = new Reloj(aerolinea, cantSegundos);
        Thread reloj = new Thread(r, "RELOJ");
        reloj.start();

        for (int i = 1; i <= cantPasajeros; i++) {
            Pasajero p = new Pasajero(aerolinea);
            Thread pasajero = new Thread(p, "PASAJERO " + i);
            pasajero.start();
        }

    }

}
