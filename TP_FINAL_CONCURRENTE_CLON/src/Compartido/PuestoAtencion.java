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

    private int turnoActual = 1;
    private int turnoAtencion = 1;

    private Lock lockEntrar = new ReentrantLock();
    private Lock lockRecibirAtencion = new ReentrantLock();
    private Condition esperaSuTurno = this.lockEntrar.newCondition();

    private Semaphore semAtender = new Semaphore(0);
    private Semaphore semSalir = new Semaphore(0);

    private Semaphore mutex = new Semaphore(1);
    private Semaphore mutex2 = new Semaphore(1);

    private int cantActual = 0, cantMax;

    public PuestoAtencion(int capMaxPuestoAtencion) {
        this.cantMax = capMaxPuestoAtencion;
    }

    public int recibirTurno() {
        int turnoMio = 0;

        try {
            this.mutex.acquire();
            turnoMio = this.turnoActual;
            this.turnoActual++;
            this.mutex.release();
        } catch (Exception e) {
        }

        return turnoMio;
    }

    public void entrar(int turnoPasajero) {

        this.lockEntrar.lock();
        try {
            while (this.cantActual > this.cantMax || turnoPasajero > this.turnoAtencion + this.cantMax - 1) {
                try {
                    System.out.println(Thread.currentThread().getName() + " no pudo entrar al puesto de atencion, se dirige al hall central.");
                    this.esperaSuTurno.await();
                } catch (Exception e) {
                }
            }

            System.out.println(Thread.currentThread().getName() + " pudo entrar al puesto de atencion.");

            try {
                this.mutex2.acquire();
                this.cantActual++;
                this.mutex2.release();
            } catch (Exception e) {
            }

        } finally {
            this.lockEntrar.unlock();
        }

    }

    public void recibirAtencion(int turnoPasajero) {

        this.lockRecibirAtencion.lock();

        while (turnoPasajero != this.turnoAtencion) {
            try {

                // Y ACA???????????????????????????????????????????????????????????????????????????????????????
                this.lockEntrar.lock();
                this.esperaSuTurno.await();
                this.lockEntrar.unlock();
            } catch (Exception e) {
            }
        }

        this.semAtender.release();

        try {
            this.semSalir.acquire();

            this.mutex2.acquire();
            this.cantActual--;
            this.mutex2.release();

        } catch (Exception e) {
        }

        this.lockRecibirAtencion.unlock();
    }

    public void atender() {
        try {
            this.semAtender.acquire();
        } catch (Exception e) {
        }

        try {
            System.out.println(Thread.currentThread().getName() + " está atendiendo al pasajero...");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " terminó de atender al pasajero.");

        } catch (Exception e) {
        }

        this.lockEntrar.lock();

        this.turnoAtencion++;

        this.semSalir.release();
        try {
            this.esperaSuTurno.signalAll();
        } finally {
            this.lockEntrar.unlock();
        }

    }

}
