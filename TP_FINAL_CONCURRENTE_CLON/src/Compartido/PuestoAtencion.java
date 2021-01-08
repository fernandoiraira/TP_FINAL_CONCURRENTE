/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compartido;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class PuestoAtencion {

    private Lock ingresarPasajero = new ReentrantLock();
    private Aeropuerto aero;

    public PuestoAtencion(Aeropuerto aeropuerto) {
        this.aero = aeropuerto;
    }

    public Lock getLock() {
        return ingresarPasajero;
    }

}
