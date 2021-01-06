/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

/**
 *
 * @author Fernando
 */
public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object tipoElem) {
        boolean exito = true;
        Nodo nuevo;

        if (this.frente == null) {
            this.frente = new Nodo(tipoElem, null);
            this.fin = this.frente;
        } else {
            nuevo = new Nodo(tipoElem, null);
            this.fin.setEnlace(nuevo);
            this.fin = nuevo;
        }

        return exito;
    }

    public boolean sacar() {
        boolean exito = true;

        if (this.frente == null) {
            exito = false;
        } else {
            this.frente = this.frente.getEnlace();
            if (this.frente == null) {
                this.fin = null;
            }
        }
        return exito;
    }

    public Object obtenerFrente() {
        Object res;

        if (this.frente == null) {
            res = null;
        } else {
            res = this.frente.getElem();
        }
        return res;
    }

    public boolean esVacia() {
        return this.frente == null;
    }

    public void vaciar() {
        this.frente = null;
        this.fin = null;
    }

    public Cola clone() {
        Cola clon;
        Nodo aux = this.frente;

        clon = new Cola();

        if (frente != null) {
            Nodo nuevo = new Nodo(aux.getElem(), null);
            clon.frente = nuevo;
            aux = aux.getEnlace();
            while (aux != null) {
                nuevo = new Nodo(aux.getElem(), null);
                clon.fin.setEnlace(nuevo);
                clon.fin = nuevo;
                aux = aux.getEnlace();
            }
        } else {
            clon.fin = aux;
        }

        return clon;

    }

    public String toString() {
        String res = "";
        Nodo aux = this.frente;

        if (aux == null) {
            res = "Cola vacia";
        } else {
            while (aux != null) {
                res = res + aux.getElem().toString();
                aux = aux.getEnlace();
            }
        }
        return res;
    }

}
