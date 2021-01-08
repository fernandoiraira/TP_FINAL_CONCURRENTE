/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Compartido.Aeropuerto;
import Hilos.Guardia;
import Hilos.Pasajero;
import Hilos.Reloj;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class main {

    public static void main(String[] args) {
        int cantPasajeros = 10;
        int cantAerolineas = 2;
        int capPuestosAtencion = 1;
        int cantSegundos = 2;

        int capMaxTren = 4;

        Aeropuerto aeropuerto = new Aeropuerto(cantAerolineas, capMaxTren, capPuestosAtencion);

        Reloj r = new Reloj(aeropuerto, cantSegundos);
        Thread reloj = new Thread(r, "RELOJ");
        reloj.start();

        for (int i = 1; i < cantAerolineas; i++) {
            Guardia g = new Guardia(aeropuerto, i);
            Thread guardia = new Thread(g, "GUARDIA " + i);
            guardia.start();
        }

        for (int i = 1; i <= cantPasajeros; i++) {
            Pasajero p = new Pasajero(aeropuerto);
            Thread pasajero = new Thread(p, "PASAJERO " + i);
            pasajero.start();
        }

    }

}
