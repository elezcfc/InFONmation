package com.example.stefanelez.infonmation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.stefanelez.infonmation.util.FontStyle;
import com.example.stefanelez.infonmation.util.Preferences;
import com.example.stefanelez.infonmation.util.Util;

/**
 * Created by Sarma on 4/3/2016.
 */
public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_layout);


        final Preferences prefs = new Preferences(this);

        final Spinner spFontStyles = (Spinner) findViewById(R.id.spFontStyles);
        ArrayAdapter<FontStyle> adapter = new ArrayAdapter<FontStyle>(this, android.R.layout.simple_list_item_1, FontStyle.values());

        spFontStyles.setAdapter(adapter);
        spFontStyles.setSelection(prefs.getFontStyle().ordinal());
        spFontStyles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        // Small size selected
                        prefs.setFontStyle(FontStyle.Small);
                        Log.d(Util.TAG,"PrefActivity: 1. item selected.");
                        Preferences.notifyStateChanged();
                        break;
                    case 1:
                        // Medium size selected
                        prefs.setFontStyle(FontStyle.Medium);
                        Log.d(Util.TAG, "PrefActivity: 2. item selected.");
                        Preferences.notifyStateChanged();

                        break;
                    case 2:
                        // Large size selected
                        prefs.setFontStyle(FontStyle.Large);
                        Log.d(Util.TAG, "PrefActivity: 3. item selected.");
                        Preferences.notifyStateChanged();

                        break;
                    default:
                        // Set medium
                        prefs.setFontStyle(FontStyle.Medium);
                        Preferences.notifyStateChanged();

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spFontStyles.setSelection(prefs.getFontStyle().ordinal());
            }
        });
    }
}
