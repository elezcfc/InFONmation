package com.example.stefanelez.infonmation.model;

import android.graphics.Bitmap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Stefan Elez on 22-Sep-16.
 */

public class OnePieceOfNews {
    private int id;
    private String title;
    private GregorianCalendar date;
    private String textHTML;
    private String imageUrl;
    private List<String> imagesURL;
    private List<Bitmap> images;


    public String getImageUrl() {
        return imageUrl;
    }


    private String text;
    public static final String ONE_PIECE_OF_NEWS_URL= "http://fonis.rs/api/get_post/?post_id=";

    public void parse(){
        Document d= Jsoup.parse(textHTML);
        text =d.text();

    }

    public void setTextHTML(String textHTML) {
        this.textHTML = textHTML;
        removeImagesFromHTML();
        removeShareButtons();
        parse();
    }

    public OnePieceOfNews(int id, String title, GregorianCalendar date, String textHTML, String imageUrl) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.textHTML = textHTML;
        removeImagesFromHTML();
        removeShareButtons();
        this.imageUrl = imageUrl;
        parse();

    }
    private void removeImagesFromHTML(){
        while(textHTML.contains("<img")){
            textHTML=textHTML.substring(0,textHTML.indexOf("<img"))+
                    textHTML.substring(textHTML.indexOf("<img")+textHTML.substring(textHTML.indexOf("<img")).indexOf(" />") + 3);
        }
    }
    private void removeShareButtons(){
        if(textHTML.contains("<!-- Simple Share Buttons"))
            textHTML=textHTML.substring(0,textHTML.indexOf("<!-- Simple Share Buttons"));
    }
    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getTextHTML() {
        return textHTML;
    }


    public boolean equals(Object o) {
        if (!(o instanceof OnePieceOfNews)) return false;
        OnePieceOfNews v = (OnePieceOfNews) o;
        if (v.getId() != id) return false;
        return true;
    }

    @Override
    public String toString() {
        return title + " " + text + " " + date.getTime() + " " + ";";
    }

    public boolean hasSubstring(String text) {
        return this.text.toLowerCase().contains(text.toLowerCase()) || this.title.toLowerCase().contains(text.toLowerCase());
    }

}
