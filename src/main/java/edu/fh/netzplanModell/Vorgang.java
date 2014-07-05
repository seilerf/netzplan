/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzplanModell;

import java.util.LinkedList;
import java.util.Observable;

/**
 *
 * @author fseiler/ M.Ullmann
 */
public class Vorgang extends Observable {
    //Paramter fuer einen Vorgang
    //Id fuer einen Vorgang
    private int vorgangId;
    //Id fuer die Referenz-Id des dazugehoerigen Netzplanes
    private int netzRefId;
    //Name des Vorgangs
    private String name;
    //Dauer des Vorgangs
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

    //Konstruktor1
    public Vorgang(int vorgId, String name, double dauer) {
        this.vorgangId = vorgId;
        this.name = name;
        this.dauer = dauer;
        this.nachf = new LinkedList<Vorgang>();
        this.vorgaenger = new LinkedList<Vorgang>();
        this.faz = 0.0;
        this.sez = 0.0;
    }
    
    //Konstruktor2
    public Vorgang(int id) {
        this.vorgangId = id;
        this.nachf = new LinkedList<Vorgang>();
        this.vorgaenger = new LinkedList<Vorgang>();
    }
    //Default-Konstruktor
    public Vorgang() {
        this.name = "";
        this.dauer = 0.0;
        this.faz = 0.0;
        this.sez = 0.0;
        this.nachf = new LinkedList<Vorgang>();
        this.vorgaenger = new LinkedList<Vorgang>();
    }

    /**
     * Getter fuer die Vorgangs-Id.
     * @return vorgangId
     */
    public int getVorgangId() {
        return vorgangId;
    }

    /**
     * Setter fuer die Vorgangs-Id.
     * @param vorgangId -> Id des Vorgangs 
     */
    public void setVorgangId(int vorgangId) {
        this.vorgangId = vorgangId;
    }

    /**
     * Getter fuer die referenzierte Netzplan-Id.
     * @return netzRefId
     */
    public int getNetzRefId() {
        return netzRefId;
    }

    /**
     * Setter fuer die referenzierte Netzplan-Id.
     * @param netzRefId -> refenzierte Netzplan-Id
     */
    public void setNetzRefId(int netzRefId) {
        this.netzRefId = netzRefId;
    }

    /**
     * Getter fuer den Namen des Vorgangs.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter fuer den Namen des Vorgangs.
     * @param name -> Name des Vorgangs
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter fuer die Dauer des Vorgangs.
     * @return dauer
     */
    public double getDauer() {
        return dauer;
    }

    /**
     * Setter fuer die Dauer des Vorgangs.
     * @param dauer -> Dauer des Vorgangs
     */
    public void setDauer(double dauer) {
        this.dauer = dauer;
    }

    /**
     * Getter fuer die fruehste Anfangszeit.
     * @return faz
     */
    public double getFaz() {
        return faz;
    }

    /**
     * Setter fuer die frueheste Anfangszeit.
     * @param faz -> fruehste Anfangszeit
     */
    public void setFaz(double faz) {
        this.faz = faz;
    }

    /**
     * Getter fuer die frueheste Endzeit.
     * @return fez
     */
    public double getFez() {
        return fez;
    }

    /**
     * Setter fuer die frueheste Endzeit.
     * @param fez -> frueheste Endzeit
     */
    public void setFez(double fez) {
        this.fez = fez;
    }

    /**
     * Getter fuer die spaeteste Anfangszeit.
     * @return saz
     */
    public double getSaz() {
        return saz;
    }

    /**
     * Setter fuer die spaetesste Anfangszeit.
     * @param saz -> spaeteste Anfangszeit
     */
    public void setSaz(double saz) {
        this.saz = saz;
    }

    /**
     * Getter fuer die spaeteste Endzeit.
     * @return sez
     */
    public double getSez() {
        return sez;
    }

    /**
     * Setter fuer die spaeteste Endzeit.
     * @param sez -> spaeteste Endzeit
     */
    public void setSez(double sez) {
        this.sez = sez;
    }
 
    /**
     * Getter fuer die Vorgaenger eines Vorgangs.
     * @param vorgaenger -> LinkedList mit Vorgaengern
     * @return vorgaenger
     */
    public LinkedList<Vorgang> getVorgaenger(LinkedList<Vorgang> vorgaenger) {
        return vorgaenger;
    }
	
	
    /**
     * Methode zum Setzen der Vorgaenger fuer einen Vorgang.
     * @param vorgaenger -> LinkedListe mit den Vorgaengern
     */
    public void setVorgaenger(LinkedList<Vorgang> vorgaenger) {
        for(int i=0; i<vorgaenger.size(); ++i) {
            this.vorgaenger.add(vorgaenger.get(i));
        }
    }
    
    /**
     * Getter fuer die Nachfolgerliste.
     * @return nachf
     */
    public LinkedList<Vorgang> getNachf() {
        return nachf;
    }

    /**
     * Methode fuer das Setzen der Nachfolger.
     * @param nachf -> LinkedListe der Nachfolger
     */
    public void setNachf(LinkedList<Vorgang> nachf) {
        for(int i=0; i<nachf.size(); ++i) {
            this.nachf.add(nachf.get(i));
        }        
    }  
    
}
