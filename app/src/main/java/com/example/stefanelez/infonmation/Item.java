package com.example.stefanelez.infonmation;

/**
 * Created by Stefan Elez on 21-Sep-16.
 */
public class Item {

    private String title;
    private String description;
    private String link;

    private Item (String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
