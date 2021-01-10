/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compartido;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class PuestoAtencion {

    private int cantActual = 0, cantMax;
    private int turnoActual = 1;
    private int turnoAtencion = 1;

    private Semaphore semAtender = new Semaphore(0);
    private Semaphore semSalir = new Semaphore(0);
    private Lock lockEntrar = new ReentrantLock();
    private Lock lockRecibirAtencion = new ReentrantLock();
    private Condition esperaAQueHayaLugar = this.lockEntrar.newCondition();
    private Condition esperaAQueSeaSuTurno = this.lockRecibirAtencion.newCondition();

    public PuestoAtencion(int capMaxPuestoAtencion) {
        this.cantMax = capMaxPuestoAtencion;
    }

    public int entrar() {
        int turnoMio;

        this.lockEntrar.lock();
        try {
            turnoMio = this.turnoActual;
            this.turnoActual++;

            this.cantActual++;

            while (this.cantActual > this.cantMax) {
                System.out.println(Thread.currentThread().getName() + " no pudo entrar al puesto de atencion, se dirige al Hall Central.");
                try {
                    this.esperaAQueHayaLugar.await();
                } catch (Exception e) {
                }
            }

            System.out.println(Thread.currentThread().getName() + " entro al puesto de atencion.");
            this.semAtender.release();
        } finally {
            this.lockEntrar.unlock();
        }

        return turnoMio;
    }

    public void recibirAtencion(int turnoPasajero) {

        this.lockRecibirAtencion.lock();

        while (this.turnoAtencion != turnoPasajero) {
            try {
                this.esperaAQueSeaSuTurno.await();
            } catch (Exception e) {
            }
        }

        try {
            this.semSalir.acquire();
        } catch (Exception e) {
        }

        this.cantActual--;
        System.out.println(Thread.currentThread().getName() + " salio del puesto de atencion, ya sabe su numero de embarque.");
        this.lockRecibirAtencion.unlock();

        this.lockEntrar.lock();
        System.out.println("AVISO A LOS DEMAS PASAJEROOOOOOOOS");
        this.esperaAQueHayaLugar.signalAll();
        this.lockEntrar.unlock();
    }

    public void atender() {
        try {
            this.semAtender.acquire();
        } catch (Exception e) {
        }

        try {
            System.out.println(Thread.currentThread().getName() + " esta atendiendo al pasajero...");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " terminó de atender al pasajero.");
            this.turnoAtencion++;

        } catch (Exception e) {
        }

        this.semSalir.release();

        this.lockRecibirAtencion.lock();
        try {
            this.esperaAQueSeaSuTurno.signalAll();
        } finally {
            this.lockRecibirAtencion.unlock();
        }

    }

}
