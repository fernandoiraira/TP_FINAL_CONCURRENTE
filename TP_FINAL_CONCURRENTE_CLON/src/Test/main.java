/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Compartido.Aeropuerto;
import Compartido.Terminal;
import Hilos.Pasajero;
import Hilos.Recepcionista;
import Hilos.Reloj;
import Hilos.Tren;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class main {

    public static void main(String[] args) {
        int cantPasajeros = 9;
        int cantAerolineas = 2;
        int capPuestosAtencion = 2;
        int cantSegundos = 3;  // Cada cantSegundos pasa 1 hora

        int capMaxTren = 4;

        Aeropuerto aeropuerto = new Aeropuerto(cantAerolineas, capMaxTren, capPuestosAtencion); // capMaxTren puede estar de mas, revisar

        Reloj r = new Reloj(aeropuerto, cantSegundos);
        Thread reloj = new Thread(r, "RELOJ");
        reloj.start();

        Terminal terminal = new Terminal(capMaxTren);

        Tren t = new Tren(terminal);
        Thread tren = new Thread(t, "TREN");
        tren.start();

        for (int i = 1; i <= cantAerolineas; i++) {
            Recepcionista rece = new Recepcionista(aeropuerto, i);
            Thread recepcionista = new Thread(rece, "RECEPCIONISTA " + i);
            recepcionista.start();
        }

        for (int i = 1; i <= cantPasajeros; i++) {
            Pasajero p = new Pasajero(aeropuerto, terminal);
            Thread pasajero = new Thread(p, "PASAJERO " + i);
            pasajero.start();
        }

    }

}
