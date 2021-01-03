/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Compartido.Aeropuerto;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class Reloj implements Runnable {

    private Aeropuerto aerolinea;
    private int pasoTiempo = 100; //100 = 1 hora
    private int frecuenciaActualizacion =1; // Pasa "pasoTiempo" cada "frecuenciaActualizacion" segundos

    public Reloj(Aeropuerto aerolinea) {
        this.aerolinea = aerolinea;
    }

    public void run() {
        while (true) {
            aerolinea.pasarTiempo(pasoTiempo);
            this.esperar();
        }
    }

    private void esperar() {
        try {
            Thread.sleep(frecuenciaActualizacion * 1000);
        } catch (Exception e) {
        }
    }

}
