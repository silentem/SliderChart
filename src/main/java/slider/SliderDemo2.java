package slider;

/*
/*
 * SliderDemo2.java
 * ---------------
 * A demo that uses a SlidingXYDataset that provides a window of the
 * underlying dataset
 * 
 * This example uses TimeSeries
 *  
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


/**
 * A demo that uses a "wrapper" dataset that provides a window of the
 * underlying dataset.
 */
public class SliderDemo2 extends JFrame {

    public static final int WINDOW = 25;
    public static final int FIRST = 0;
    static final int COUNT = 200;

    public SliderDemo2(String title) {
        super(title);
        setContentPane(new DemoPanel());
    }

    public static void main(String[] args) {
        SliderDemo2 demo = new SliderDemo2("SliderDemo Demo2");
        demo.pack();
        demo.setVisible(true);
    }

    private static class DemoPanel extends JPanel
            implements ChangeListener {

        private ChartPanel chartPanel;

        private JFreeChart chart;

        private JSlider slider;

        private SlidingXYDataset dataset;

        public DemoPanel() {
            super(new BorderLayout());
            this.chart = createChart();
            this.chartPanel = new ChartPanel(this.chart);
            this.chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
            add(this.chartPanel);
            JPanel dashboard = new JPanel(new BorderLayout());
            this.slider = new JSlider(0, COUNT - WINDOW - 1, 0);
            slider.setPaintLabels(true);
            slider.setPaintTicks(true);
            slider.setMajorTickSpacing(WINDOW);
            this.slider.addChangeListener(this);
            dashboard.add(this.slider);
            add(dashboard, BorderLayout.SOUTH);
        }

        private JFreeChart createChart() {

            XYDataset dataset1 = createDataset(
                    "Random 1", 100.0, new Minute(), COUNT
            );

            JFreeChart chart1 = ChartFactory.createTimeSeriesChart(
                    "Sliding Demo 2", "Time of Day", "Value",
                    dataset1, true, true, false);
            XYPlot plot = chart1.getXYPlot();
            DateAxis xaxis = (DateAxis) plot.getDomainAxis();
            xaxis.setAutoRange(true);
            return chart1;
        }


        private XYDataset createDataset(String name, double base,
                                        RegularTimePeriod start, int count) {
            TimeSeries series1 = getRandomTimeSeries(name + "1", 100.0,
                    start, count);
            TimeSeries series2 = getRandomTimeSeries(name + "2", 75.0,
                    start, count / 2);
            TimeSeriesCollection tsc = new TimeSeriesCollection();
            tsc.addSeries(series1);
            tsc.addSeries(series2);
            this.dataset = new SlidingXYDataset(tsc, FIRST, WINDOW);
            return dataset;
        }


        private TimeSeries getRandomTimeSeries(String name, double base,
                                               RegularTimePeriod start, int count) {
            TimeSeries ts = new TimeSeries(name, start.getClass());
            RegularTimePeriod period = start;
            double value = base;
            for (int i = 0; i < count; i++) {
                ts.add(period, value);
                period = period.next();
                value = value * (1 + (Math.random() - 0.495) / 10.0);
            }
            return ts;
        }

        public void stateChanged(ChangeEvent event) {
            int value = this.slider.getValue();
            this.dataset.setFirstItemIndex(value);
        }
    }
}