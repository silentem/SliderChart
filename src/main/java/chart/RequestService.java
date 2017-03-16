package chart;

import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Created by Влад on 15.03.2017.
 */
public class RequestService {

    private String stockSymbol;
    private String interval;
    private GregorianCalendar calendar;
    private int countBar;
    public RequestService(){
        calendar = new GregorianCalendar();
        countBar = 10;
    }

    public void setCountBar(int countBar) {
        this.countBar = countBar;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public AbstractXYDataset getDataSet(){

        DefaultOHLCDataset result;
        //This is the data needed for the dataset
        OHLCDataItem[] data;
        //This is where we go get the data, replace with your own data source
        data = getData(stockSymbol, interval);
        //Create a dataset, an Open, High, Low, Close dataset
        result = new DefaultOHLCDataset(stockSymbol, data);
        return result;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public String getInterval() {
        return interval;
    }

    private OHLCDataItem[] getData(String stockSymbol, String interval){
        ArrayList<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>(countBar);

        StringBuilder string = new StringBuilder();
        string.append("https://www.google.com/finance/getprices?");
        string.append("q=" + stockSymbol);
        string.append("&i=" + interval);

        System.out.println(string.toString());
        BufferedReader reader = null;
        try {

            URL url = new URL(string.toString());

            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            int indexRow= 0;
            while (indexRow++<7){
                reader.readLine();
            }

            int countMinutes = Integer.parseInt(this.interval)/60;
            String line;
            while((line = reader.readLine()) != null){
                if(line.indexOf("TIME")== -1){
                    if(line.startsWith("a")){
                        String subStr = line.substring(1, line.indexOf(','));
                        Date date = new Date(Long.parseLong(subStr));
                    }else{
                        calendar.add(Calendar.MINUTE, countMinutes);
                        OHLCDataItem item = parseString(line.substring(line.indexOf(",")+1));

                        dataItems.add(item);
                    }
                }
                if(indexRow++ > (countBar + 7)){
                    break;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }


        OHLCDataItem[] items = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
        return items;
    }
    private OHLCDataItem parseString(String sourse){
        StringTokenizer tokenizer = new StringTokenizer(sourse, ",");


        ///DATE,CLOSE,HIGH,LOW,OPEN,VOLUME
        System.out.println("parse string: " + sourse);
        double close  = Double.parseDouble(tokenizer.nextToken());
        double hight = Double.parseDouble(tokenizer.nextToken());
        double low  =Double.parseDouble(tokenizer.nextToken());
        double open = Double.parseDouble(tokenizer.nextToken());
        double volume = Double.parseDouble(tokenizer.nextToken());
        return new OHLCDataItem(calendar.getTime(), open, hight, low, close,volume);
    }
}
