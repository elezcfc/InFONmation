package com.example.stefanelez.infonmation;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.example.stefanelez.infonmation.model.Vesti;
import com.example.stefanelez.infonmation.model.OnePieceOfNews;
import com.example.stefanelez.infonmation.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by Stefan Elez on 22-Sep-16.
 */

public class DownloadVesti extends IntentService{

    public static final int UPDATE_PROGRESS = 8344;
    private static int mDownloadedPages = 0;
    public static final int NEWS_ACTIVITY_CALLER = 0;
    public static final int NEWS_VIEW_ACTIVITY_CALLER = 1;
    public static final int IMAGE_CALLER = 2;
    public static final int SPLASH_SCREEN_CALLER = 3;
    private HttpURLConnection connection = null;
    private String text = null;
    private BufferedReader in = null;
    private URL url;
    public static volatile boolean newsDownloaded = true;

    public DownloadVesti() {
        super("DownloadVesti");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (newsDownloaded)
            switch (intent.getExtras().getInt("caller")) {
                case SPLASH_SCREEN_CALLER: {
                    try {
                        downloadNews(intent);
                    } catch (NoInternetException e) {
                        Bundle resultData = new Bundle();
                        resultData.putInt("progress", -1);
                        ResultReceiver receiver = intent.getParcelableExtra("receiver");
                        if (receiver != null)
                            receiver.send(UPDATE_PROGRESS, resultData);

                        newsDownloaded = true;
                        VestiActivity.mCurrentPage = 1;

                    }
                    break;
                }
                case NEWS_ACTIVITY_CALLER: {
                    try {
                        downloadNews(intent);
                    } catch (NoInternetException e) {
                        Bundle resultData = new Bundle();
                        resultData.putInt("progress", -1);
                        ResultReceiver receiver = intent.getParcelableExtra("receiver");
                        if (receiver != null)
                            receiver.send(UPDATE_PROGRESS, resultData);
                        newsDownloaded = true;
                        VestiActivity.mCurrentPage--;

                    }
                    break;
                }
                case NEWS_VIEW_ACTIVITY_CALLER: {
                    try {
                        downloadOnePieceOfNews(intent);
                    } catch (NoInternetException e) {
                        Bundle resultData = new Bundle();
                        resultData.putInt("progress", -1);
                        ResultReceiver receiver = intent.getParcelableExtra("receiver");
                        if (receiver != null)
                            receiver.send(UPDATE_PROGRESS, resultData);


                    }
                    break;
                }
                case IMAGE_CALLER: {
                    // downloadImage("");
                    break;
                }
            }

    }

    private void downloadOnePieceOfNews(Intent intent) throws NoInternetException {
        int id = intent.getExtras().getInt("onePieceOfNewsId");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        try {
            url = new URL(OnePieceOfNews.ONE_PIECE_OF_NEWS_URL + id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String textJSON = downloadJSON(url);
        setText(id, textJSON);

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 0);

        if (receiver != null)
            receiver.send(UPDATE_PROGRESS, resultData);
        //TODO - add broadcast
    }


    private void downloadNews(Intent intent) throws NoInternetException {
        newsDownloaded = false;
        int[] pageNumber = intent.getExtras().getIntArray("pageNumber");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");


        for (int i = 0; i < pageNumber.length; i++) {

            // TODO - add error feedback to user
            try {
                url = new URL(Vesti.NEWS_URL + pageNumber[i]);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }

            //Downloading news and making news objects
            String textJSON = downloadJSON(url);
            makeTheNews(textJSON);

            Bundle resultData = new Bundle();
            resultData.putInt("progress", 0);
            if (receiver != null)
                receiver.send(UPDATE_PROGRESS, resultData);
            //   News.currentList=News.newsList;

        }
        for (int i = 0; i < Vesti.getNewsList().size(); i++) {
            Log.d(Util.TAG, "Vest " + i + ":" + Vesti.getNewsList().get(i).toString());
        }
        newsDownloaded = true;
        if (pageNumber[0] == 1) {
            sendStickyBroadcast(new Intent(Vesti.NEWS_DOWNLOADED_INTENT));
            Log.d(Util.TAG, "Broadcast is sent.");
        }
    }

    private String downloadJSON(URL url) throws NoInternetException {
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            text = "";
            boolean procitano = false;
            while (!procitano) {
                String red = in.readLine();
                procitano = (red == null) ? true : false;
                text += red;
            }
            return text;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // showAlertDialog();
            throw new NoInternetException("Isključen vam je internet");
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text;
    }


    private void makeTheNews(String tekstJSON) {
        try {
            if (tekstJSON == null) return;
            JSONObject sadrzajStrane = new JSONObject(tekstJSON);
            JSONArray vesti = sadrzajStrane.getJSONArray("posts");
            for (int i = 0; i < vesti.length(); i++) {
                JSONObject vest = vesti.getJSONObject(i);
                int id = vest.getInt("id");
                String title = vest.getString("title");
                String textHTML = vest.getString("content");
                GregorianCalendar date = createDate(vest.getString("date"));
                String imageURL = null;
                if (vest.has("thumbnail_images")) {
                    JSONObject thumbnailJSON = vest.getJSONObject("thumbnail_images");
                    imageURL = thumbnailJSON.getJSONObject("full").getString("url");
                }
                OnePieceOfNews v = new OnePieceOfNews(id, title, date, textHTML, imageURL);
                if (!Vesti.getNewsList().contains(v))
                    Vesti.addNews(v);
            }
            mDownloadedPages++;
            Log.d(Util.TAG, "makeTheNews: page downloaded: " + mDownloadedPages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setText(int id, String textJSON) {
        try {
            JSONObject postJSON = new JSONObject(textJSON).getJSONObject("post");
            OnePieceOfNews post = Vesti.findOnePieceOfNewsByID(id);
            post.setTextHTML(postJSON.getString("content"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GregorianCalendar createDate(String datumS) {
        int godina = Integer.parseInt(datumS.substring(0, 4));
        int mesec = Integer.parseInt(datumS.substring(5, 7)) - 1;
        int dan = Integer.parseInt(datumS.substring(8, 10));
        int sat = Integer.parseInt(datumS.substring(11, 13));
        int minut = Integer.parseInt(datumS.substring(14, 16));
        int sekund = Integer.parseInt(datumS.substring(17, 19));
        return new GregorianCalendar(godina, mesec, dan, sat, minut, sekund);
    }


    @Override
    public void onDestroy() {
        Log.d(Util.TAG, "DownloadVesti is stopped.");
        super.onDestroy();

    }

    class NoInternetException extends Exception {
        public NoInternetException(String msg) {
            super(msg);
        }
    }
}
