package com.example.stefanelez.infonmation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jelica on 9/21/2016.
 */

public class PocetnaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_pocetna);
        final Button chooseButton = (Button) findViewById(R.id.choose_id);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent settingIntent = new Intent(PocetnaActivity.this, OdabirActivity.class);
                PocetnaActivity.this.startActivity(settingIntent);
            }
        });
        final Button chooseButton2 = (Button) findViewById(R.id.obavestenja_id);
        chooseButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent settingIntent = new Intent(PocetnaActivity.this, MainActivity.class);
                PocetnaActivity.this.startActivity(settingIntent);
            }
        });

    }
}
