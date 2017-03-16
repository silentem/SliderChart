package chart;


import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Влад on 15.03.2017.
 */
public class CandleStick extends JPanel {
    private JFreeChart chart;
    private String stockSymbol;
    private NumberAxis yAxis;
    private DateAxis xAxis;
    private CandlestickRenderer render;
    private XYDataset dataset;
    private XYPlot mainPlot;
    private CandlestickRenderer renderer;

    public CandleStick() {

        yAxis = new NumberAxis("Price");
        xAxis = new DateAxis("Time");
        renderer = new CandlestickRenderer();

        yAxis.setAutoRange(true);
        xAxis.setAutoRange(true);
        stockSymbol = "BIDU";

        mainPlot = new XYPlot(dataset, xAxis, yAxis, renderer);
        mainPlot.setDomainPannable(true);
        mainPlot.setRangePannable(true);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setDrawVolume(false);
        mainPlot.setDomainPannable(true);
        yAxis.setAutoRangeIncludesZero(false);
        xAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
        chart = new JFreeChart(stockSymbol, null, mainPlot, false);

    }

    public void setDataset(AbstractXYDataset dataset) {
        mainPlot.setDataset(dataset);

    }

    public JFreeChart getChart() {
        return chart;
    }

}
