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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class PuestoAtencion {

    private Cola colaPasajeros = new Cola(); // LinkedTransferQueue

    private Lock ingresarPasajero = new ReentrantLock();
    private Lock lockSacarPasajero = new ReentrantLock();
    private Condition esperaAQueHayaPasajero = lockSacarPasajero.newCondition();

    private BlockingQueue<Pasajero> asd;

    public PuestoAtencion(int capMaxPuestoAtencion) {
        this.asd = new ArrayBlockingQueue<Pasajero>(capMaxPuestoAtencion);
    }

    public void ingresar(Pasajero p) {
        this.ingresarPasajero.lock();
        try {

            this.colaPasajeros.poner(p);
            System.out.println(Thread.currentThread().getName() + " ingreso al puesto de atencion.");

        } catch (Exception e) {
        } finally {
            this.ingresarPasajero.unlock();
        }

        this.lockSacarPasajero.lock();
        try {
            this.esperaAQueHayaPasajero.signal();
        } finally {
            this.lockSacarPasajero.unlock();
        }
    }

    public Cola getCola() {
        return this.colaPasajeros;
    }

    public Pasajero transferirPasajeroDesdeHallCentral() {
        Pasajero p;

        this.lockSacarPasajero.lock();
        try {
            while (this.colaPasajeros.esVacia()) {
                try {
                    this.esperaAQueHayaPasajero.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PuestoAtencion.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            p = (Pasajero) this.colaPasajeros.obtenerFrente();
            this.colaPasajeros.sacar();

            try {

                this.asd.put(p);
                System.out.println(Thread.currentThread().getName() + " ingreso a un pasajero a la sala de Atencion");
            } catch (Exception e) {
            }

        } finally {
            this.lockSacarPasajero.unlock();
        }
        return p;
    }
}
