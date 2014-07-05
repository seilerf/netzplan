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
public class Netzplan extends Observable{
    //Parameter eines Netzplanes
    //Der Name des Netzplanes
    private String name;
    //Die Id des Netzplanes
    private int id;
    //Anzahl der Vorgaenge
    private int anzahl;
    //Start-Wert des Netzplanes
    private double start;
    //End-Wert des Netzplanes
    private double ende;
    //Gesamter Puffer
    private double gesamtPuffer;
    //Freier Puffer
    private double freierPuffer;

    /**
     * Default Konstruktor
     */
    public Netzplan() {    
    }

    /**
     * Konstruktur für den Netzplan
     */
    public Netzplan(int id, String name, double startZeit, double endZeit){
        this.id = id;
        this.name = name;
        this.start = startZeit;
        this.ende = endZeit;
        this.gesamtPuffer = 0;
        this.freierPuffer = 50;
        
    }
    
    /**
     * Getter fuer den Gesamt-Puffer.
     * @return gesamtPuffer
     */
    public double getGesamtPuffer() {
        return gesamtPuffer;
    }

    /**
     * Setter fuer den Gesamt-Puffer.
     * @param gesamtPuffer -> Wert des Gesamt-Puffers 
     */
    public void setGesamtPuffer(double gesamtPuffer) {
        this.gesamtPuffer = gesamtPuffer;
    }

    /**
     * Getter fuer den Freien-Puffer.
     * @return freierPuffer
     */
    public double getFreierPuffer() {
        return freierPuffer;
    }

    /**
     * Setter fuer den Freien-Puffer.
     * @param freierPuffer -> Wert des Freien-Puffers
     */
    public void setFreierPuffer(double freierPuffer) {
        this.freierPuffer = freierPuffer;
    }
 
    /**
     * Getter fuer den Namen des Netzplanes.
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setter fuer den Namen des Netzplanes.
     * @param name -> Name des Netzplanes
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Getter fuer die Id des Netzplanes.
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Getter fuer die Anzahl.
     * @return anzahl
     */
    public int getAnzahl() {
        return anzahl;
    }

    /**
     * Setter fuer die Anzahl.
     * @param anzahl -> Anzahl an Vorgaengen im Netzplan
     */
    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    /**
     * Getter fuer den Start-Wert.
     * @return start
     */
    public double getStart() {
        return start;
    }

    /**
     * Setter fuer den Start-Wert.
     * @param start -> Start-Wert
     */
    public void setStart(double start) {
        this.start = start;
    }

    /**
     * Getter fuer den End-Wert.
     * @return ende
     */
    public double getEnde() {
        return ende;
    }

    /**
     * Setter fuer den End-Wert
     * @param ende -> End-Wert
     */
    public void setEnde(double ende) {
        this.ende = ende;
    }
}
