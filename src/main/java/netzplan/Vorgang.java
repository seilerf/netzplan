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
public class Vorgang extends Observable {
    
    private int vorgangId;
    private int netzRefId;
    private String name;
    private double dauer;
    // Frühester Anfangszeitpunkt
    private double faz;
    // Frühester Endzeitpunkt
    private double fez;
    // Spätester Anfangszeitpunkt
    private double saz;
    // Spätester Endzeitpunkt
    private double sez;
    // Vorgänger
    private LinkedList<Vorgang> vorgaenger;
    // Nachfolger
    private LinkedList<Vorgang> nachf;

    public Vorgang(int vorgId, String name, double dauer) {
        this.vorgangId = vorgId;
        this.name = name;
        this.dauer = dauer;
        this.nachf = new LinkedList<Vorgang>();
        this.vorgaenger = new LinkedList<Vorgang>();
        this.faz = 0.0;
        this.sez = 0.0;
    }
    
    public Vorgang(String name, double dauer, int netzRefId) {
        this.name = name;
        this.dauer = dauer;
        this.netzRefId = netzRefId;
    }
    
    public Vorgang(int id) {
        this.vorgangId = id;
    }
    
    public Vorgang() {
        this.name = "";
        this.dauer = 0.0;
        this.faz = 0.0;
        this.sez = 0.0;
        this.nachf = null;
    }
    
    public Vorgang(String name){
        this.name = name;
        this.dauer = 0.0;
        this.faz = 0.0;
        this.sez = 0.0;
        this.nachf = null;
    }
    
    public int getVorgangId() {
        return vorgangId;
    }

    public void setVorgangId(int vorgangId) {
        this.vorgangId = vorgangId;
    }

    public int getNetzRefId() {
        return netzRefId;
    }

    public void setNetzRefId(int netzRefId) {
        this.netzRefId = netzRefId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDauer() {
        return dauer;
    }

    public void setDauer(double dauer) {
        this.dauer = dauer;
    }

    public double getFaz() {
        return faz;
    }

    public void setFaz(double faz) {
        this.faz = faz;
    }

    public double getFez() {
        return fez;
    }

    public void setFez(double fez) {
        this.fez = fez;
    }

    public double getSaz() {
        return saz;
    }

    public void setSaz(double saz) {
        this.saz = saz;
    }

    public double getSez() {
        return sez;
    }

    public void setSez(double sez) {
        this.sez = sez;
    }
    
    public double getFEZ() {
        // Evtl. Fehlberechnung abfangen, wenn noch kein fAnfang initialisiert
        return (this.faz + this.dauer);
    }
    
    public LinkedList<Vorgang> getVorgaenger() {
        return vorgaenger;
    }

    public void setVorgaenger(LinkedList<Vorgang> vorgaenger) {
        for(int i=0; i<vorgaenger.size(); ++i) {
            this.vorgaenger.add(vorgaenger.get(i));
        }
    }

    public LinkedList<Vorgang> getNachf() {
        return nachf;
    }

    public void setNachf(LinkedList<Vorgang> nachf) {
        for(int i=0; i<nachf.size(); ++i) {
            this.nachf.add(nachf.get(i));
        }
            
    }
    
}
