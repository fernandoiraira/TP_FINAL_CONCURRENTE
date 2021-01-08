/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compartido;

import Utiles.Cola;
import Utiles.Vuelo;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class Aeropuerto {

    private int hora = 300;
    private int capMaxTren, capActualTren = 0;
    private int cantAerolineas;
    private int[] capacidadesAtencion;
    private int[] turnosAerolineas;
    private int[] turnosDesdeCero;
    private boolean atendiendo = false;
    private Vuelo[] vuelosAerolineas;
    private Cola[] ordenEntrada;

    private Semaphore mutex = new Semaphore(1);
    private Semaphore semOtorgarVuelo = new Semaphore(1);

    private Lock ingresarAeropuerto = new ReentrantLock();
    private Condition esperarPorLaHora = ingresarAeropuerto.newCondition();

    public Aeropuerto(int cantidadAerolineas, int capTren, int capPuestosAtencion) {
        Random t = new Random();

        this.cantAerolineas = cantidadAerolineas;
        this.capMaxTren = capTren;
        this.capacidadesAtencion = new int[cantAerolineas];
        this.ordenEntrada = new Cola[cantAerolineas];
        this.turnosAerolineas = new int[cantAerolineas];
        this.turnosDesdeCero = new int[cantAerolineas];

        this.vuelosAerolineas = new Vuelo[20];

        for (int i = 0; i < cantAerolineas; i++) {
            this.capacidadesAtencion[i] = capPuestosAtencion;
            this.turnosAerolineas[i] = 1;
            this.turnosDesdeCero[i] = 1;
        }

        for (int i = 0; i < 20; i++) {
            this.vuelosAerolineas[i] = new Vuelo(t.nextInt(cantidadAerolineas) + 1, t.nextInt(20) + 1, 1700 + t.nextInt(4001));
        }
    }

    public Object[] irAPuestoDeInformes() {
        Object[] resultado = new Object[2];
        Vuelo vuelo = null;
        Random r = new Random();
        int turnoPasajero;

        // Esta parte seguramente la voy a cambiar, porque dice que el aeropuerto esta abierto siempre, pero solamente ATIENDEN de 06:00 a22:00
        this.ingresarAeropuerto.lock();
        try {
            while (!atendiendo) {
                System.out.println(Thread.currentThread().getName() + " no pudo ingresar al aeropuerto porque esta cerrado");
                try {
                    this.esperarPorLaHora.await();
                } catch (Exception e) {
                }
            }
        } finally {
            this.ingresarAeropuerto.unlock();
        }

        System.out.println(Thread.currentThread().getName() + " esta ubicando su puesto de atencion...");
        try {
            Thread.sleep(r.nextInt(5000));
        } catch (Exception e) {
        }

        try {
            this.semOtorgarVuelo.acquire();

            // Toma 1 vuelo de los 20 que se generaron (1 por cada puesto de Embarque)
            vuelo = this.vuelosAerolineas[r.nextInt(20)];

            // Se le asigna el turno correspondiente al puesto de atencion al que se tiene que dirigir
            turnoPasajero = this.turnosDesdeCero[vuelo.getAerolinea() - 1];

            // Se sube el turno al puesto de atencion al que se dirigio el pasajero
            this.turnosDesdeCero[vuelo.getAerolinea() - 1]++;

            // Se guarda el vuelo y el turno de Pasajero en un arreglo de Object (que depende del puesto de informe al que se dirige), para luego devolverselo al hilo Pasajero 
            resultado[0] = vuelo;
            resultado[1] = turnoPasajero;

            this.semOtorgarVuelo.release();
        } catch (Exception e) {
        }

        return resultado;
    }

    // TENGO QUE EVITAR QUE ENTREN EN HORARIOS QUE NO SE PUEDEN
    public void pasarTiempo(int pasoTiempo) {
        if (this.hora == 2300) {
            this.hora = 0;
            System.out.println("Hora actual: 0:00 hs");
        } else {
            this.hora += pasoTiempo;
            System.out.println("Hora actual: " + this.hora / 100 + ":00 hs");

            if (this.hora == 600) {
                System.out.println("El aeropuerto abrió sus puertas.");
                this.ingresarAeropuerto.lock();
                atendiendo = true;
                try {
                    this.esperarPorLaHora.signalAll();
                } finally {
                    this.ingresarAeropuerto.unlock();
                }
            } else if (this.hora == 2200) {
                System.out.println("El aeropuerto cerró sus puertas.");
                atendiendo = false;
            }
        }

    }

    public void atender() {

    }

    public void dirigirseAPuestoDeAtencion(int numAerolinea) {

        try {
            this.mutex.acquire();
            int capCentroAtencion = this.capacidadesAtencion[numAerolinea - 1];
            if (capCentroAtencion > 0) {
                this.capacidadesAtencion[numAerolinea - 1] -= 1;
                System.out.println(Thread.currentThread().getName() + " entró al puesto de atencion de la aerolinea " + numAerolinea);
            } else {
                System.out.println("El puesto de atención de la aerolinea " + numAerolinea + " está lleno, " + Thread.currentThread().getName() + " se dirige al hall central a esperar.");

            }
            this.mutex.release();

        } catch (Exception e) {
        }

    }
}
