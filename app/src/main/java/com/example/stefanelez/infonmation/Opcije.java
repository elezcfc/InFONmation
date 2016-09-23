package com.example.stefanelez.infonmation;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Stefan Elez on 23-Sep-16.
 */

public class Opcije {
    private static String CLASS_NAME;
    public Opcije () {
        CLASS_NAME = getClass().getName();
    }

    protected boolean bazeCheck;
    protected boolean programskiCheck;
    private static String BAZE = "baze";
    private static String PROGRAMSKI = "programski";

    protected boolean obavestenjaCheck;
    protected boolean rezultatiCheck;
    private static String OBAVESTENJA = "obavestenja";
    private static String REZULTATI = "rezultati";

    public boolean isBazeCheck(Activity activity) {
        SharedPreferences bazePref = activity.getPreferences(Activity.MODE_PRIVATE);
        if(bazePref.contains(BAZE)){
            bazeCheck = bazePref.getBoolean(BAZE, false);
        }

        return bazeCheck;
    }

    public void setBazeCheck(boolean bazeCheck, Activity activity) {
        SharedPreferences bazePref = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = bazePref.edit();
        editor.putBoolean(BAZE, bazeCheck);
        editor.apply();
        this.bazeCheck = bazeCheck;
    }

    public boolean isProgramskiCheck(Activity activity) {
        SharedPreferences pjPref = activity.getPreferences(Activity.MODE_PRIVATE);
        if(pjPref.contains(PROGRAMSKI)){
            programskiCheck = pjPref.getBoolean(PROGRAMSKI, false);
        }
        return programskiCheck;
    }

    public void setProgramskiCheck(boolean programskiCheck, Activity activity) {
        SharedPreferences pjPref = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pjPref.edit();
        editor.putBoolean(PROGRAMSKI, programskiCheck);
        editor.apply();
        this.programskiCheck = programskiCheck;
    }


    public boolean isObavestenjaCheck(Activity activity) {
        SharedPreferences oPref = activity.getPreferences(Activity.MODE_PRIVATE);
        if(oPref.contains(OBAVESTENJA)){
            obavestenjaCheck = oPref.getBoolean(OBAVESTENJA, false);
        }
        return obavestenjaCheck;
    }

    public void setObavestenjaCheck(boolean obavestenjaCheck, Activity activity) {
        SharedPreferences oPref = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = oPref.edit();
        editor.putBoolean(OBAVESTENJA, obavestenjaCheck);
        editor.apply();
        this.obavestenjaCheck = obavestenjaCheck;
    }

    public boolean isRezultatiCheck(Activity activity) {
        SharedPreferences rPref = activity.getPreferences(Activity.MODE_PRIVATE);
        if(rPref.contains(REZULTATI)){
            rezultatiCheck = rPref.getBoolean(REZULTATI, false);
        }
        return rezultatiCheck;
    }

    public void setRezultatiCheck(boolean rezultatiCheck, Activity activity) {
        SharedPreferences rPref = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = rPref.edit();
        editor.putBoolean(REZULTATI, rezultatiCheck);
        editor.apply();
        this.rezultatiCheck = rezultatiCheck;
    }
}
