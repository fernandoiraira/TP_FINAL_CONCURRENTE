/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Compartido.Terminal;

/**
 *
 * @author Fernando
 */
public class Tren implements Runnable {

    private Terminal terminal;

    public Tren(Terminal terminal) {
        this.terminal = terminal;
    }

    public void run() {
        while (true) {
            this.terminal.iniciarViaje();
        }

    }

}
