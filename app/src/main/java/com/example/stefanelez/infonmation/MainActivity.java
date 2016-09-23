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
    public ArrayList<Item> chooseList (){
        DownloadAndParse d = new DownloadAndParse();
        OdabirActivity o = new OdabirActivity();
        String url = "http://is.fon.bg.ac.rs/feed/";
        ArrayList<Item> svaObavestenjaSviPredmeti =new ArrayList<Item>(); //sadrzi sva obavestenja
        svaObavestenjaSviPredmeti = d.readRSS(url);
        Opcije opcije = ((Upit)getApplication()).getOpcije();
        //if(o.obavestenja.isChecked()){
        try {
            if (opcije.isBazeCheck(this)) {
                ArrayList<Item> obavestenjaBaze = new ArrayList<Item>();
                for (int i = 0; i < svaObavestenjaSviPredmeti.size(); i++) {
                    if (svaObavestenjaSviPredmeti.get(i).getTitle().contains("Базе") || svaObavestenjaSviPredmeti.get(i).getTitle().contains("База")) {
                        obavestenjaBaze.add(svaObavestenjaSviPredmeti.get(i));
                    }
                }
                return obavestenjaBaze;
            }
        }catch (NullPointerException e){
            System.out.println("Ne radi prvi upit");
        }
        try {
            if (opcije.isProgramskiCheck(this)) {
                ArrayList<Item> obavestenjaProgramski = new ArrayList<Item>();
                for (int i = 0; i < svaObavestenjaSviPredmeti.size(); i++) {
                    if (svaObavestenjaSviPredmeti.get(i).getTitle().contains("Програмски")) {
                        obavestenjaProgramski.add(svaObavestenjaSviPredmeti.get(i));
                    }
                }
                return obavestenjaProgramski;
            }
        }catch (NullPointerException e){
            System.out.println("Ne radi prvi upit");
        }
        //}
        //if(o.rezultati.isChecked()){
            if(opcije.isBazeCheck(this)){
                ArrayList<Item> rezultatiBaze = new ArrayList<Item>();
                for(int i=0; i<svaObavestenjaSviPredmeti.size(); i++){
                    if((svaObavestenjaSviPredmeti.get(i).getTitle().contains("Базе") || svaObavestenjaSviPredmeti.get(i).getTitle().contains("База")) &&
                            svaObavestenjaSviPredmeti.get(i).getTitle().contains("Резултати") || svaObavestenjaSviPredmeti.get(i).getTitle().contains("Rezultati")){
                        rezultatiBaze.add(svaObavestenjaSviPredmeti.get(i));
                    }
                }
                return rezultatiBaze;
            }
            if(opcije.isProgramskiCheck(this)){
                ArrayList<Item> rezultatiProgramski = new ArrayList<Item>();
                for(int i=0; i<svaObavestenjaSviPredmeti.size(); i++){
                    if((svaObavestenjaSviPredmeti.get(i).getTitle().contains("Програмски")) &&
                            svaObavestenjaSviPredmeti.get(i).getTitle().contains("Резултати")){
                        rezultatiProgramski.add(svaObavestenjaSviPredmeti.get(i));
                    }
                }
                return rezultatiProgramski;
            }
        //}//*/
        return svaObavestenjaSviPredmeti;
    }

    private void init(){


        mListView = (ListView) findViewById(R.id.Exp);
        mAdapter = new ListViewAdapter(this, chooseList());
        mListView.setAdapter(mAdapter);

    }
}
