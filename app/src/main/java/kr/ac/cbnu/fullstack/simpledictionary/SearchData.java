package kr.ac.cbnu.fullstack.simpledictionary;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JiyoungPark on 2016. 5. 3..
 */
public class SearchData {
    private String word;
    private String mean;
    private int count;
    private String date;

    public SearchData() {
        this.word = null;
        this.mean = null;
        this.count = 1;
        this.date = null;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public void setCount() {
        count++;
    }
    public int getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        Date tempDate = new Date();
        this.date = dateFormat.format(tempDate);
    }

}
