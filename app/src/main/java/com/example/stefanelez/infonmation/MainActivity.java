package com.example.stefanelez.infonmation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    PullRSS pull = new PullRSS("http://is.fon.bg.ac.rs/feed/");
    List headlines;
    List links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button chooseButton = (Button) findViewById(R.id.choose_id);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent settingIntent = new Intent(MainActivity.this, OdabirActivity.class);
                MainActivity.this.startActivity(settingIntent);
            }
        });

        headlines = new ArrayList();
        links = new ArrayList();

        try {
            URL url = new URL("http://is.fon.bg.ac.rs/feed/");

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            // We will get the XML from an input stream
            xpp.setInput(new InputStreamReader(url.openStream(),"UTF8"));

        /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
         * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
         * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
         * so we should skip the "<title>" tag which is a child of "<channel>" tag,
         * and take in consideration only "<title>" tag which is a child of "<item>"
         *
         * In order to achieve this, we will make use of a boolean variable.
         */
            boolean insideItem = false;

            // Returns the type of current event: START_TAG, END_TAG, etc..
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem)
                            headlines.add(xpp.nextText()); //extract the headline
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem)
                            links.add(xpp.nextText()); //extract the link of article
                    }
                }else if(eventType== XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                    insideItem=false;
                }

                eventType = xpp.next(); //move to next element
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
    /*public void onButtonClicked() {
        final Button refreshButton = (Button) findViewById(R.id.refresh_id);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prepareListData();
            }
        });
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

    /*public void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add(pull.getTitle());
        List<String> title = new ArrayList<String>();
        title.add(pull.getDescription());
    }*/

    /*public void PullRSS (String urlAdress) {

        try {
            URL urlRSS = new URL(urlAdress);

            BufferedReader in = new BufferedReader(new InputStreamReader(urlRSS.openStream(),"UTF8"));


        }
        catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        System.out.println("Bad URL");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Something went wrong with reading");
        }
    }*/



}
