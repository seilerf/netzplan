/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzview;
import edu.fh.netzcontroller.BmgController;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;
import edu.fh.netzplanModell.ChartModel;
import edu.fh.netzplanModell.Vorgang;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.renderer.category.StackedBarRenderer;




/**
 *
 * @author Anton
 */
 public class BmgView extends ApplicationFrame {

    private Vorgang vorgangs;
   
    private ChartModel chartmodel;
    /**
     * Chartmodel konstruktor bei Aufruf neuer Bmg Chart
     *
     * @param Titel 
     * @param controller
     * @param chartmodel 
     */
    public BmgView(String title, final BmgController controller, final ChartModel chartmodel) {
        super(title);
        this.chartmodel=chartmodel;
        CategoryDataset dataset = this.chartmodel.createDatasetBMG();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
        chartPanel.setFocusable(true);

        chartPanel.addChartMouseListener(new ChartMouseListener() {

    public void chartMouseClicked(ChartMouseEvent e) {
       
        
        controller.mousecklicked(e);

    }
            public void chartMouseMoved(ChartMouseEvent event) {

            }

});
        
        chartPanel.addKeyListener(new KeyListener() {
        
    
    /**
     * @param e
     * ruft auf dem Controller die Keypressed Methode auf
     */
            public void keyTyped(KeyEvent e) {
                System.out.println("Taste wurde gedrückt");
                try {
                    controller.keyPressed(e);
                } catch (SQLException ex) {
                    Logger.getLogger(GanttView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            public void keyReleased(KeyEvent e) {
                
            }

            public void keyPressed(KeyEvent e) {
                
            }
        
        
        });
    }


    /**
     * Kreiert das BmgChart.
     * 
     * @param categorydataset  
     * 
     * @return das Bmgchart.
     */
        private static JFreeChart createChart(CategoryDataset categorydataset)   
    {   
        JFreeChart jfreechart = ChartFactory.createStackedBarChart("BMG Auslastung", "Betriebsmittelgruppen", "Dauer", categorydataset, PlotOrientation.VERTICAL, false, false, false);   
        CategoryPlot categoryplot = jfreechart.getCategoryPlot();   
        StackedBarRenderer extendedstackedbarrenderer = new StackedBarRenderer();   
        extendedstackedbarrenderer.setItemLabelsVisible(true);   
        //extendedstackedbarrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());   
        extendedstackedbarrenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());   
        categoryplot.setRenderer(extendedstackedbarrenderer);   
        ValueAxis valueaxis = categoryplot.getRangeAxis();   
        valueaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());   
          
        return jfreechart;   
    }  

    
    

}
