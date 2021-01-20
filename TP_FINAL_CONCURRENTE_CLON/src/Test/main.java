/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Compartido.Aeropuerto;
import Hilos.Pasajero;
import Hilos.Recepcionista;
import Hilos.Reloj;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class main {

    public static void main(String[] args) {
        int cantPasajeros = 25;
        int cantAerolineas = 5;
        int capPuestosAtencion = 2;
        int cantSegundos = 3;

        int capMaxTren = 4;

        Aeropuerto aeropuerto = new Aeropuerto(cantAerolineas, capMaxTren, capPuestosAtencion);

        Reloj r = new Reloj(aeropuerto, cantSegundos);
        Thread reloj = new Thread(r, "RELOJ");
        reloj.start();

        for (int i = 1; i <= cantAerolineas; i++) {
            Recepcionista rece = new Recepcionista(aeropuerto, i);
            Thread recepcionista = new Thread(rece, "RECEPCIONISTA " + i);
            recepcionista.start();
        }

        for (int i = 1; i <= cantPasajeros; i++) {
            Pasajero p = new Pasajero(aeropuerto);
            Thread pasajero = new Thread(p, "PASAJERO " + i);
            pasajero.start();
        }

    }

}
