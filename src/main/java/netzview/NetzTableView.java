/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzview;

/**
 *
 * @author Anton
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
 
public class NetzTableView extends JPanel {
   
    
    String[] titles = new String[]{ "A", "B", "C", "D" };
 
		// Das Model das wir verwenden werden. Hier setzten wir gleich die
		// Titel, aber es ist später immer noch möglich weitere Columns oder
		// Rows hinzuzufügen.
		final DefaultTableModel model = new DefaultTableModel( titles, 0 );
 
		// Das JTable initialisieren
		JTable table = new JTable( model );
    
}