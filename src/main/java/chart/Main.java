package chart;

import org.jfree.chart.ChartPanel;
import slider.SlidingXYDataset;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Влад on 15.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chart");

        CandleStick chart = new CandleStick();
        ChartPanel panel = new ChartPanel(chart.getChart());
        panel.setDomainZoomable(false);
        panel.setMouseZoomable(true);
        frame.setContentPane(panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        RequestService service = new RequestService();


        //установить интервал для сервиса
        service.setInterval("60");
        //установить компанию
        service.setStockSymbol("AIR");

        //установить количество бар
        service.setCountBar(1000);



        //установить данние для графика
        chart.setDataset(service.getDataSet());


        panel.setDomainZoomable(false);
        panel.setFillZoomRectangle(false);
        panel.setRangeZoomable(false);
        panel.zoomInRange(0, 0);
        chart.getChart().setTitle(service.getStockSymbol() + " interval: " + service.getInterval());



    }
}
