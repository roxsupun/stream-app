package com.kinesisstream.app.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by supun on 2/20/18.
 */
public class NewsArticle {

    private final static ObjectMapper JSON = new ObjectMapper();

    static {
        JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private String rank;
    private String title;
    private Integer numOfPages;
    private String comments;


    public NewsArticle() {
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(Integer numOfPages) {
        this.numOfPages = numOfPages;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] toJsonAsBytes() {
        try {
            return JSON.writeValueAsBytes(this);
        } catch (IOException e) {
            return null;
        }
    }

    public static NewsArticle fromJsonAsBytes(byte[] bytes) {
        try {
            return JSON.readValue(bytes, NewsArticle.class);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("Rank %s | Title %s. with %d number of pages.",
                rank, title, numOfPages);
    }

    public NewsArticle(String rank, String title, Integer numOfPages) {
        this.rank = rank;
        this.title = title;
        this.numOfPages = numOfPages;
    }
}
