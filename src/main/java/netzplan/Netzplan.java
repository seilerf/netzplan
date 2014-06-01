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
 * @author fseiler/ M.Ullmann
 */
public class Netzplan extends Observable{
    private String name;
    private int id;
    private int anzahl;
    private double start;
    private double ende;
    private double gesamtPuffer;
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
    
    
    public double getGesamtPuffer() {
        return gesamtPuffer;
    }

    public void setGesamtPuffer(double gesamtPuffer) {
        this.gesamtPuffer = gesamtPuffer;
    }

    public double getFreierPuffer() {
        return freierPuffer;
    }

    public void setFreierPuffer(double freierPuffer) {
        this.freierPuffer = freierPuffer;
    }
    private LinkedList<Vorgang> vorgaenge;

    public Netzplan(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }
    
    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnde() {
        return ende;
    }

    public void setEnde(double ende) {
        this.ende = ende;
    }

    public LinkedList<Vorgang> getVorgaenge() {
        return vorgaenge;
    }
    
    public void setVorgaenge(LinkedList<Vorgang> vorgaenge) {
        this.vorgaenge = vorgaenge;
    }
}
