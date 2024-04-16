package com.example.expensetrackersystem;

public class tipItems {
    private final int imageResource;
    private final String url;

    public tipItems(int imageResource, String url) {
        this.imageResource = imageResource;
        this.url = url;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getUrl() {
        return url;
    }
}
