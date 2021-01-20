/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compartido;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Fernando
 */
public class Terminal {

    private CyclicBarrier barreraTren;
    private Semaphore mutex = new Semaphore(1);
    private Semaphore semAsiento;
    private int[] cuantosSeBajanEnCadaLado = {0, 0, 0};
    int await;
    private Semaphore semIniciarViaje = new Semaphore(0);
    private int capActualTren = 0, capMaxTren;
    private Semaphore bajarATerminalA = new Semaphore(0);
    private Semaphore bajarATerminalB = new Semaphore(0);
    private Semaphore bajarATerminalC = new Semaphore(0);

    public Terminal(int capTren) {
        this.barreraTren = new CyclicBarrier(capTren);
        this.capMaxTren = capTren;
        this.semAsiento = new Semaphore(this.capMaxTren);
    }

    public void entrarAtren(int puestoDeEmbarque) {

        try {
            this.mutex.acquire();
            this.semAsiento.acquire();
            if (puestoDeEmbarque >= 1 && puestoDeEmbarque <= 7) {
                this.cuantosSeBajanEnCadaLado[0] += 1; //HAY UNO MAS QUE SE QUIERE BAJAR EN LA TERMINAL A
            } else if (puestoDeEmbarque >= 8 && puestoDeEmbarque <= 15) {
                this.cuantosSeBajanEnCadaLado[1] += 1; //HAY UNO MAS QUE SE QUIERE BAJAR EN LA TERMINAL B
            } else {
                this.cuantosSeBajanEnCadaLado[2] += 1; //HAY UNO MAS QUE SE QUIERE BAJAR EN LA TERMINAL C
            }
            System.out.println(Thread.currentThread().getName() + " esta subiendo al tren...");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " subio al tren.");

            this.mutex.release();

            this.await = this.barreraTren.await();     
            if (await == 0) {
                this.semIniciarViaje.release();
            }

        } catch (Exception e) {
        }

    }

    public void iniciarViaje() {
        System.out.println(Thread.currentThread().getName() + " esta a la espera de pasajeros...");
        try {
            this.semIniciarViaje.acquire();

            System.out.println(Thread.currentThread().getName() + " inicia el viaje a la terminal C...");
            Thread.sleep(4000);
            System.out.println(Thread.currentThread().getName() + " llego a la terminal C.");

            if (this.cuantosSeBajanEnCadaLado[2] != 0) {
                this.bajarATerminalC.release();
                this.semIniciarViaje.acquire();
            }

            System.out.println(Thread.currentThread().getName() + " inicia el viaje a la terminal B...");
            Thread.sleep(4000);
            System.out.println(Thread.currentThread().getName() + " llego a la terminal B.");

            if (this.cuantosSeBajanEnCadaLado[1] != 0) {
                this.bajarATerminalB.release();
                this.semIniciarViaje.acquire();
            }

            System.out.println(Thread.currentThread().getName() + " inicia el viaje a la terminal A...");
            Thread.sleep(4000);
            System.out.println(Thread.currentThread().getName() + " llego a la terminal A.");

            if (this.cuantosSeBajanEnCadaLado[0] != 0) {
                this.bajarATerminalA.release();
                this.semIniciarViaje.acquire();
            }

            System.out.println(Thread.currentThread().getName() + " termino el recorrido completo, esta volviendo...");
            Thread.sleep(4000);
            System.out.println(Thread.currentThread().getName() + " llego al lugar de inicio.");

            this.semAsiento.release(this.capMaxTren);

        } catch (Exception e) {
        }

    }

    public void bajar(int puestoDeEmbarque) {
        if (puestoDeEmbarque >= 1 && puestoDeEmbarque <= 7) {
            try {
                this.bajarATerminalA.acquire();
                this.cuantosSeBajanEnCadaLado[0] -= 1;
                System.out.println(Thread.currentThread().getName() + " esta bajando del tren...");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " se bajo del tren en la terminal A.");

                if (this.cuantosSeBajanEnCadaLado[0] == 0) {
                    this.semIniciarViaje.release();
                } else {
                    this.bajarATerminalA.release();
                }
            } catch (Exception e) {
            }

        } else if (puestoDeEmbarque >= 8 && puestoDeEmbarque <= 15) {
            try {
                this.bajarATerminalB.acquire();
                this.cuantosSeBajanEnCadaLado[1] -= 1;
                System.out.println(Thread.currentThread().getName() + " esta bajando del tren...");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " se bajo del tren en la terminal B.");

                if (this.cuantosSeBajanEnCadaLado[1] == 0) {
                    this.semIniciarViaje.release();
                } else {
                    this.bajarATerminalB.release();
                }
            } catch (Exception e) {
            }

        } else {
            try {
                this.bajarATerminalC.acquire();
                this.cuantosSeBajanEnCadaLado[2] -= 1;
                System.out.println(Thread.currentThread().getName() + " esta bajando del tren...");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " se bajo del tren en la terminal C.");

                if (this.cuantosSeBajanEnCadaLado[2] == 0) {
                    this.semIniciarViaje.release();
                } else {
                    this.bajarATerminalC.release();
                }

            } catch (Exception e) {
            }

        }

    }
}
