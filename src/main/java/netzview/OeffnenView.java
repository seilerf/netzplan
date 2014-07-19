/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzview;

import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;

/**
 *
 * @author Anton, Florian Seiler
 */
public class OeffnenView extends AbstractView{

    private boolean editable;
    private JButton btnOeffnen;
    private JScrollPane scrollpane;
    
    public OeffnenView(JTable table) {   
        this.scrollpane = new JScrollPane(table);
        this.setTitle("Netzplan öffnen");
        this.btnOeffnen = new JButton("Öffnen");
        this.setLayout();
    }
    
    /**
     * Mit dieser Methode wird die GUI gezeichnet.
     * @author Florian Seiler
     */
    private void setLayout(){
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                    .addComponent(scrollpane)
                    .addGroup(TRAILING, layout.createSequentialGroup()
                        .addGap(0, 323, Short.MAX_VALUE)
                        .addComponent(btnOeffnen)))
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollpane, PREFERRED_SIZE, 146, PREFERRED_SIZE)
                .addPreferredGap(RELATED)
                .addPreferredGap(RELATED)
                .addComponent(btnOeffnen)
                .addGap(0, 109, Short.MAX_VALUE))
        );

        pack();
    }
    
    /**
     * Methode, die den Controllern zur Verfügung steht, um Listener zu platzieren. 
     * @author Florian Seiler
     */
    public void setBtnOeffnenListener(ActionListener l){
        btnOeffnen.addActionListener(l);
    }
    
}

