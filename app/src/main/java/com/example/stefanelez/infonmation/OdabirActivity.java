package com.example.stefanelez.infonmation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class OdabirActivity extends AppCompatActivity {

    public CheckBox baze;
    public CheckBox programski;
    public RadioButton rezultati;
    public RadioButton obavestenja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odabir);

        Intent intent = getIntent();

        baze = (CheckBox) findViewById(R.id.bp_checkBox);
        programski = (CheckBox) findViewById(R.id.pj_checkBox);

        rezultati = (RadioButton) findViewById(R.id.rezultati_button);
        obavestenja = (RadioButton) findViewById(R.id.obavestenja_button);

        obavestenja.setChecked(true);

        Opcije opcije = ((Upit)getApplication()).getOpcije();
        baze.setChecked(opcije.isBazeCheck(this));
        programski.setChecked(opcije.isProgramskiCheck(this));

        obavestenja.setChecked(opcije.isBazeCheck(this));
        rezultati.setChecked(opcije.isBazeCheck(this));
        //baze.setChecked(true);
       // programski.setChecked(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        Opcije opcije = ((Upit)getApplication()).getOpcije();
        opcije.setBazeCheck(baze.isChecked(), this);
        opcije.setProgramskiCheck(programski.isChecked(), this);

        if(obavestenja.isChecked()){
            opcije.setObavestenjaCheck(obavestenja.isChecked(), this);
        }else{
            opcije.setRezultatiCheck(rezultati.isChecked(),this);
        }
    }

    public void goBack(View v) {
        finish();
    }

}
