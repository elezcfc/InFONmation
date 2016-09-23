package com.example.stefanelez.infonmation;

import android.app.Application;

/**
 * Created by Stefan Elez on 23-Sep-16.
 */

public class Upit extends Application {

    protected Opcije opcije;

    public Opcije getOpcije() {
        if(opcije == null){
            opcije = new Opcije();
        }
        return opcije;
    }

    public void setOpcije(Opcije opcije) {
        this.opcije = opcije;
    }
}
