/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzplan;

import java.util.Observable;
import java.util.TreeSet;

/**
 *
 * @author fseiler
 */
public class Netzplan extends Observable{
    private int anzahl;
    private int start;
    private int ende;
    private TreeSet<Vorgang> vorgaenge;

    public TreeSet<Vorgang> getVorgaenge() {
        return vorgaenge;
    }

    public Netzplan(int anzahl, int start, int ende) {
        this.anzahl = anzahl;
        this.start = start;
        this.ende = ende;
        this.vorgaenge = new TreeSet<Vorgang>();
    }

    public Netzplan() {
        this.anzahl = 0;
        this.start = 0;
        this.ende = 0;
        this.vorgaenge = new TreeSet<Vorgang>();
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
        this.vorgaenge.add(vorgang);
    }
    
}
