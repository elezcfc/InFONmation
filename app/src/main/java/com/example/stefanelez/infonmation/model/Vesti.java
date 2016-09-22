package com.example.stefanelez.infonmation.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by Stefan Elez on 22-Sep-16.
 */

public abstract class Vesti {
    public static final String NEWS_DOWNLOADED_INTENT = "com.vesti.fonis.news_downloaded";

    private static ArrayList<OnePieceOfNews> newsList = new ArrayList<>();
    public static final String NEWS_URL = "http://fonis.rs/api/get_posts/?page=";

    //  public static ArrayList<OnePieceOfNews> currentList=new ArrayList<>();

    public static OnePieceOfNews findOnePieceOfNewsByID(int id){
        for (OnePieceOfNews v: newsList
                ) {
            if (v.getId() == id) return v;
        }
        return null;
    }

    public static ArrayList<OnePieceOfNews> searchNews(String text) {
        // removes all extra space between words

        text = text.replaceAll("\\s+", " ");

        // ArrayList<OnePieceOfNews> list=new ArrayList<>();
        ArrayList<OnePieceOfNews> currentList=new ArrayList<>();
        for (int i=0;i< newsList.size();i++){
            if(newsList.get(i).hasSubstring(text))
                currentList.add(newsList.get(i));
        }
        return currentList;
    }

    public synchronized static ArrayList<OnePieceOfNews> getNewsList(){
        return newsList;
    }

    public synchronized static void addNews(OnePieceOfNews onePieceOfNews){
        newsList.add(onePieceOfNews);
    }

}
