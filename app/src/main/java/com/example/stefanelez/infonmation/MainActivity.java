package com.example.stefanelez.infonmation;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stefanelez.infonmation.adapter.ListViewAdapter;
import com.example.stefanelez.infonmation.model.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private ListViewAdapter mAdapter;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        init();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                String url = mAdapter.getItem(pos).getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
    }

    private void init(){
        DownloadAndParse d = new DownloadAndParse();
        String url = "http://is.fon.bg.ac.rs/feed/";

        mListView = (ListView) findViewById(R.id.Exp);
        ArrayList<Item> list123=new ArrayList<Item>();
        list123 = d.readRSS(url);//list123 sadrzi sva obavestenja
        ArrayList<Item> listSamoRezultati=new ArrayList<Item>();
        for(int i=0; i<list123.size(); i++){
            if(list123.get(i).getTitle().contains("Резултати") || list123.get(i).getTitle().contains("Rezultati") ){
                listSamoRezultati.add(list123.get(i));
            }
        }
        /*Item a=new Item();
        a.setTitle("fsfsfs");
        a.setDescription("dsadadadadad");
        list123.add(a);*/
        mAdapter = new ListViewAdapter(this, list123);
        mListView.setAdapter(mAdapter);

    }


}
