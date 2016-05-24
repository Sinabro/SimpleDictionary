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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        Date tempDate = new Date();
        date = dateFormat.format(tempDate);

        this.date = date;
    }
}
