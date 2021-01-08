/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compartido;

import Hilos.Pasajero;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class PuestoAtencion {

    private LinkedTransferQueue linkedPasajeros = new LinkedTransferQueue();

    private BlockingQueue<Pasajero> block;

    public PuestoAtencion(int capMaxPuestoAtencion) {
        this.block = new ArrayBlockingQueue<Pasajero>(capMaxPuestoAtencion);
    }

    public void ponerPasajero(Pasajero p) {
        this.linkedPasajeros.add(p);
    }

    public void transferirPasajero() {
        Pasajero res = null;

        try {
            res = (Pasajero) this.linkedPasajeros.take();
        } catch (Exception e) {
        }

        try {
            this.block.put(res);
            System.out.println(Thread.currentThread().getName() + " ingreso a un pasajero a la sala de Atencion");
        } catch (Exception e) {
        }
    }

    public void atenderPasajero() {

        try {
            System.out.println(Thread.currentThread().getName() + " esta esperando a un pasajero...");
            this.block.take();
            
            System.out.println(Thread.currentThread().getName() + " atendio al pasajero.");

        } catch (Exception e) {
        }

    }
}
