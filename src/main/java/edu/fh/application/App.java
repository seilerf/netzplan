package edu.fh.application;

import datenbank.SQLConnect;
import netzcontroller.NetzplanController;
import netzview.StartView;


public class App 
{
    public static void main(String[] args) {
        
        //Anzahl möglicher Vorgänge im Netzplan
        final int MAX=50;
        
        //SQLConnect sql = new SQLConnect();
        NetzplanController netzplanCon = new NetzplanController();
        netzplanCon.erstelleNetzplan();
        StartView startView = new StartView();
       // startView.pack();
        
        
        

        

    
        
       
        
    }
}
