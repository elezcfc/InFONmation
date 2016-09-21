package com.example.stefanelez.infonmation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Stefan Elez on 20-Sep-16.
 */
public class PullRSS {

    private String title = "";
    private String link = "";
    private String description = "";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    private volatile boolean parsingComplete = true;

    public PullRSS(String url){
        this.urlString = url;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void parseAndStoreXML(XmlPullParser myParser){
        int event;
        String text = null;

        try{
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT){
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equals("title")){
                            title = text;
                        }else if(name.equals("link")){
                            link = text;
                        }else if(name.equals("description")){
                            description = text;
                        }
                        else{
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    conn.connect();
                    InputStream in = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myParser = xmlFactoryObject.newPullParser();

                    myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myParser.setInput(in, "UTF-8");

                    parseAndStoreXML(myParser);
                    in.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
