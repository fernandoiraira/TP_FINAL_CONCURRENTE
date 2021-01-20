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
    //El vuelo deberia saber su puesto de atencion de antemano? o deberian indicarselo en el puesto de atencion?

    public Vuelo(int aerolinea, int horaVuelo) {
        this.aerolinea = aerolinea;
        this.horaVuelo = horaVuelo;

        //El puesto de embarque se lo asignan en el pueso de Atencion
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
