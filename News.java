package com.ashuguy.gulfnewsrss;

/**
 * Created by dell on 5/17/2017.
 */

public class News {

    private String title;
    private String link;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Desciption: " + description + "\n";
    }
}
