/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compartido;

import java.util.Random;
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

    private Lock lockSimple = new ReentrantLock();
    private Condition espera = this.lockSimple.newCondition();

    private Semaphore semAtender = new Semaphore(0);
    private Semaphore semSalir = new Semaphore(0, true);

    private Semaphore mutex = new Semaphore(1);

    private int cantActual = 0, cantMax;

    public PuestoAtencion(int capMaxPuestoAtencion) {
        this.cantMax = capMaxPuestoAtencion;
    }

    public int recibirTurno() {
        // Este metodo da un turno a cada pasajero, para poder respetar el orden de llegada
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
        // Si hay espacio, y si su turno le permite entrar (basandose en la capacidad), entra al puesto de atencion, sino se dirige al hall central
        boolean fallo = false;

        this.lockSimple.lock();
        try {
            while (this.cantActual > this.cantMax || turnoPasajero > this.turnoAtencion + this.cantMax - 1) {
                try {
                    if (!fallo) {
                        System.out.println(Thread.currentThread().getName() + " no pudo entrar al puesto de atencion, se dirige al hall central.");
                        fallo = true;
                    }
                    this.espera.await();
                } catch (Exception e) {
                }
            }

            System.out.println(Thread.currentThread().getName() + " pudo entrar al puesto de atencion.");

            //Ahora hay un pasajero mas dentro del puesto de atencion
            this.cantActual++;

        } finally {
            this.lockSimple.unlock();
        }

    }

    public void pedirAtencion(int turnoPasajero) {

        this.lockSimple.lock();
        try {
            while (turnoPasajero != this.turnoAtencion) {
                try {
                    this.espera.await();
                } catch (Exception e) {
                }
            }

            //Aviso a la recepcionista que ya me puede atender
            this.semAtender.release();

        } finally {
            this.lockSimple.unlock();
        }
    }

    public int recibirAtencion() {
        Random r = new Random();
        int puestoEmbarque = 0;
        // A pesar de que varios hilos quedan a la espera para poder salir, se respeta el orden de salida porque el semaforo semSalir tiene el fairness en true.        
        // El semSalir tiene como maximo 1 permiso disponible, porque en el metodo anterior se traban todos los hilos (ya que la atencion debe ser de a 1 Pasajero a la vez), 
        // excepto el que le corresponde por su turno.
        try {
            // Espero a que la recepcionista termine de atenderme
            this.semSalir.acquire();
            puestoEmbarque = r.nextInt(20) + 1;
        } catch (Exception e) {
        }

        // Tomo el lock para modificar la variable cantActual
        this.lockSimple.lock();
        try {
            this.cantActual--;
        } finally {
            this.lockSimple.unlock();
        }

        return puestoEmbarque;
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

        this.lockSimple.lock();
        try {
            this.turnoAtencion++;

            this.semSalir.release();

            this.espera.signalAll();
        } finally {
            this.lockSimple.unlock();
        }

    }

}
