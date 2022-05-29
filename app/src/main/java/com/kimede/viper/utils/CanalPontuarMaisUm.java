package com.kimede.viper.utils;

import android.os.AsyncTask;

import org.jsoup.Jsoup;

import java.io.IOException;


public class CanalPontuarMaisUm extends AsyncTask<String, Void, Void> {
    protected Void doInBackground(String... strArr) {
        String str = "http://techrevolution.com/api/canal/addrank/" + strArr[0];
        try {

             Jsoup.connect(str).ignoreContentType(true).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
