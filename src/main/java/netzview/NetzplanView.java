/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzview;

import static java.lang.System.exit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import netzcontroller.NetzplanController;

/**
 *
 * @author Maxwell
 */
public class NetzplanView extends JFrame implements Observer{

    /**
     * Creates new form NetzplanView
     */
    public NetzplanView(NetzplanController netzplan) {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        menuNeu = new javax.swing.JMenuItem();
        menuEnd = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu3.setText("Netzplan");

        menuNeu.setText("Neu...");
        menuNeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNeuActionPerformed(evt);
            }
        });
        jMenu3.add(menuNeu);

        menuEnd.setText("Beenden");
        menuEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEndActionPerformed(evt);
            }
        });
        jMenu3.add(menuEnd);

        jMenuBar2.add(jMenu3);

        jMenu4.setText("Bearbeiten");
        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuNeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNeuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuNeuActionPerformed

    private void menuEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEndActionPerformed
        System.out.println("Beenden");
        exit(0);// TODO add your handling code here:
    }//GEN-LAST:event_menuEndActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem menuEnd;
    private javax.swing.JMenuItem menuNeu;
    // End of variables declaration//GEN-END:variables

    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
