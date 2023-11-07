package com.example.appdocbao;

public class RSSItem {
    private String title;
    private String description;
    private String pubDate;
    private String imageUrl;
    private String link;


    public RSSItem(String title, String description, String pubDate, String imageUrl, String link) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
