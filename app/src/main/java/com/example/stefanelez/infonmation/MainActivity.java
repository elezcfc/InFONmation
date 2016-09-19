package com.example.stefanelez.infonmation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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


    }


}
