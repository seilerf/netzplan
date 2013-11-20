/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.application;

/**
 *
 * @author fseiler
 */
public class Netzplanung {
    
    public static void main(String[] args) {
        
        //Anzahl möglicher Vorgänge
        final int MAX=50;
        //Vorgangsdauer
        double[] dauer = new double[MAX];
        //frühester Vorgangsbeginn
        double[] fAnf = new double[MAX];
        //spätestes Vorgangsende
        double[] sEnde = new double[MAX];
        
        boolean[][] nachf = new boolean[MAX][MAX];
        int g, h, i, j, k, m; //Laufvariablen
        
        // Initialisierung des Netzplans
        int startZeit = 0;
        int endZeit = 20;
        
// Anzahl der Vorgänge im Netzplan
        int anzahl = 5;
        dauer[0] = 1;
        dauer[1] = 3;
        dauer[2] = 2;
        dauer[3] = 5;
        dauer[4] = 2;
        
        for (g=0; g<anzahl; ++g){
            fAnf[g] = 0.0;
            sEnde[g] = 0.0;
            
            for (h=0; h<anzahl; ++h){
                nachf[g][h] = false;
            }
        }
        
        // Abhängigkeiten setzen
        nachf[0][1] = true;
        nachf[0][2] = true;
        nachf[1][4] = true;
        nachf[3][4] = true;
        
        // fruehester Anfangszeitpunkt
        for (i=0; i<anzahl; ++i){
            // Maximum der fruhesten Endzeitpunkte aller Vorgänger durch Vorwärtsrechnen
            fAnf[i] = startZeit;
            for (j=0; j<anzahl; ++j){
                if (true == nachf[i][j]){
                    double fEnd = fAnf[j] + dauer[j];
                    if(fEnd > fAnf[i]){
                        fAnf[i] = fEnd;
                    }
                }
            }
        }
        
        //spätesten Endzeitpunkt ermitteln
        for (i=anzahl-1; i>-1; --i){
            // Minimum der spätesten Anfangszeitpunkte der Nachfolger durch Rückwärtsrechnung
            sEnde[i] = endZeit;
            for(j=0; j<anzahl; ++j){
                if (true == nachf[i][j]){
                    double sAnf = sEnde[j] - dauer[j];
                    if (sEnde[i] > sAnf){
                        sEnde[i] = sAnf;
                    }
                }
            }
        }
        
        // Ausgabe des Netzplans
        for (m=0; m<anzahl; ++m){
            System.out.println(
                "V" + m+1 +": [FA, FE]: ["
                + fAnf[m] + ".. "
                + (fAnf[m] + dauer[m])
                + "], [SA, SE]: ["
                + sEnde[m] + "]\n");
        }
        System.out.println("\n");
    }
}
