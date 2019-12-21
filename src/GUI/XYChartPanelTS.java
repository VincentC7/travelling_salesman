package GUI;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;

public class XYChartPanelTS extends XChartPanel {

    public XYChartPanelTS(Chart chart) {
        super(chart);
    }

    /**
     * Cosntructeur du panel de chart avec creation de la chart
     * @param nameXAxis nom de l'axe X
     * @param nameYAxis nom de l'axe Y
     * @param seriesName nom de la serie de la chart
     * @param width taille de la chart
     * @param height hauteur de la charte
     */
    public XYChartPanelTS(String nameXAxis,String nameYAxis,String seriesName,int width, int height ){
        super(new XYChartBuilder().width(width).height(height).xAxisTitle(nameXAxis).yAxisTitle(nameYAxis).build());
        XYChart c = (XYChart) this.getChart();
        c.addSeries(seriesName, new double []{ 0 },new double [] {0});
    }

    /**
     * Creation des données à ajouter dans la chart avant de les mettre à jour
     * @param name nom de la series
     * @param yAxis tableau de toutes les valeurs de la fitness
     * @param nbGeneration nombre de generation dans l'algorithme
     */
    public void addData(String name,double [] yAxis,int nbGeneration){
        double [] xAxis = new double[nbGeneration];
        for (int i = 1;i<=nbGeneration;i++){
            xAxis[i-1]=i;
        }
        this.updateChart(name,xAxis,yAxis);

    }

    /**
     * Methode de msie à jour de la chart du panel
     * @param name nom de la series mise à jour
     * @param xAxis tableau de double des valeurs de l'axe X
     * @param yAxis tableau de double des valeurs de l'axe Y
     */
    private void updateChart(String name,double [] xAxis,double [] yAxis){
        XYChart c = (XYChart) this.getChart();
        c.updateXYSeries(name,xAxis,yAxis,null);
        this.updateUI();
    }
}
