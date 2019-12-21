package GUI;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;

public class XYChartPanelTS extends XChartPanel {

    public XYChartPanelTS(Chart chart) {
        super(chart);
    }

    public XYChartPanelTS(String nameXAxis,String nameYAxis,String seriesName,int width, int height ){
        super(new XYChartBuilder().width(width).height(height).xAxisTitle(nameXAxis).yAxisTitle(nameYAxis).build());
        XYChart c = (XYChart) this.getChart();
        c.addSeries(seriesName, new double []{ 0 },new double [] {0});
    }

    public void addData(String name,double [] yAxis,int nbGeneration){
        double [] xAxis = new double[nbGeneration];
        for (int i = 1;i<=nbGeneration;i++){
            xAxis[i-1]=i;
        }
        this.updateChart(name,xAxis,yAxis);

    }

    private void updateChart(String name,double [] xAxis,double [] yAxis){
        XYChart c = (XYChart) this.getChart();
        c.updateXYSeries(name,xAxis,yAxis,null);
        this.updateUI();
    }
}
