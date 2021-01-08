/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compartido;

import Hilos.Pasajero;
import Utiles.Cola;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class PuestoAtencion {

    private Cola colaPasajeros = new Cola(); // LinkedTransferQueue
    private LinkedTransferQueue linkedPasajeros = new LinkedTransferQueue();

    private BlockingQueue<Pasajero> asd;

    public PuestoAtencion(int capMaxPuestoAtencion) {
        this.asd = new ArrayBlockingQueue<Pasajero>(capMaxPuestoAtencion);
    }

    public void ponerPasajero(Pasajero p) {
        this.linkedPasajeros.add(p);
    }

    public void retirarPasajero() {
        Pasajero res = null;

        try {
            res = (Pasajero) this.linkedPasajeros.take();
        } catch (Exception e) {
        }

        try {
            this.asd.put(res);
            System.out.println(Thread.currentThread().getName() + " ingreso a un pasajero a la sala de Atencion");
        } catch (Exception e) {
        }

    }
}
