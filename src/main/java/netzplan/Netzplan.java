/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzplan;

import java.util.LinkedList;
import java.util.Observable;

/**
 *
 * @author fseiler
 */
public class Netzplan extends Observable{
    private int anzahl;
    private int start;
    private int ende;
    private LinkedList<Vorgang> vorgaenge;

    public LinkedList<Vorgang> getVorgaenge() {
        return vorgaenge;
    }

    public Netzplan(int anzahl, int start, int ende) {
        this.anzahl = anzahl;
        this.start = start;
        this.ende = ende;
        this.vorgaenge = new LinkedList<Vorgang>();
    }

    public Netzplan() {
        this.anzahl = 0;
        this.start = 0;
        this.ende = 0;
        this.vorgaenge = new LinkedList<Vorgang>();
    }
    
    

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnde() {
        return ende;
    }

    public void setEnde(int ende) {
        this.ende = ende;
    }

    public void fuegeEin(Vorgang vorgang) {
        if (this.vorgaenge.isEmpty())
            this.vorgaenge.addFirst(vorgang);
        else
            this.vorgaenge.addLast(vorgang);
    }
    
}
