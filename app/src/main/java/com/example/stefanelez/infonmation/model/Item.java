package com.example.stefanelez.infonmation.model;

/**
 * Created by Jelica on 9/22/2016.
 */

public class Item {
    String title;
    String link;
    String description;

    /*public OneInformation(String title, String link, String description){
        this.title = title;
        this.link = link;
        this.description = description;
    }*/

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "OneInformation{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
