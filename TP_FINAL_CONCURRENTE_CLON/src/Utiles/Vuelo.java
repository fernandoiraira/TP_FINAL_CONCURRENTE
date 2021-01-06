/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

/**
 *
 * @author Fernando Iraira <fmiraira@gmail.com>
 */
public class Vuelo {

    private int aerolinea;
    private int puestoDeEmbarque;
    private int horaVuelo;

    public Vuelo(int aerolinea, int puestoDeEmbarque, int horaVuelo) {
        this.aerolinea = aerolinea;
        this.puestoDeEmbarque = puestoDeEmbarque;
        this.horaVuelo = horaVuelo;
    }

    public void setAerolinea(int aerolinea) {
        this.aerolinea = aerolinea;
    }

    public void setPuestoDeEmbarque(int puestoDeEmbarque) {
        this.puestoDeEmbarque = puestoDeEmbarque;
    }

    public void setHoraVuelo(int horaVuelo) {
        this.horaVuelo = horaVuelo;
    }

    public int getAerolinea() {
        return aerolinea;
    }

    public int getPuestoDeEmbarque() {
        return puestoDeEmbarque;
    }

    public int getHoraVuelo() {
        return horaVuelo;
    }

}
