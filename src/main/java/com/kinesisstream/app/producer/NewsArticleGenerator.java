package com.kinesisstream.app.producer;

import com.kinesisstream.app.model.NewsArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by supun on 2/20/18.
 */
public class NewsArticleGenerator {
    private final Random random = new Random();
    private static final List<NewsArticle> NEWS_ARTICLES = new ArrayList<NewsArticle>();
    static {
        NEWS_ARTICLES.add(new NewsArticle("021","The Hunger Games",246));
        NEWS_ARTICLES.add(new NewsArticle("022","Harry Potter and the Order of the Phoenix",656));
        NEWS_ARTICLES.add(new NewsArticle("023","Classic How-to",876));
        NEWS_ARTICLES.add(new NewsArticle("024","Identify and Solve a Problem",886));
        NEWS_ARTICLES.add(new NewsArticle("025","Make A Statement",436));
        NEWS_ARTICLES.add(new NewsArticle("026","Strike A Note Of Controversy",566));
        NEWS_ARTICLES.add(new NewsArticle("027","Shorter Titles Are Great",856));
        NEWS_ARTICLES.add(new NewsArticle("028","Ask Questions",136));
        NEWS_ARTICLES.add(new NewsArticle("029","Use Headlines That Offer Explanations",496));
        NEWS_ARTICLES.add(new NewsArticle("031","Go For An “Intrigue” Style",686));
        NEWS_ARTICLES.add(new NewsArticle("033","Try A “Finality”",336));
        NEWS_ARTICLES.add(new NewsArticle("034","Craft A “Top List”",556));
        NEWS_ARTICLES.add(new NewsArticle("035","Who Else Wants date",786));
        NEWS_ARTICLES.add(new NewsArticle("036","Little Known Ways to",646));
        NEWS_ARTICLES.add(new NewsArticle("037","Admiral of the Fleet",466));
        NEWS_ARTICLES.add(new NewsArticle("038","Baron or Baroness",776));
        NEWS_ARTICLES.add(new NewsArticle("041","Commissioner of Baseball",968));
        NEWS_ARTICLES.add(new NewsArticle("042","You forgot about the Onion!",275));
        NEWS_ARTICLES.add(new NewsArticle("043","The Tombstone Epitaph",267));
        NEWS_ARTICLES.add(new NewsArticle("044","You missed the Sacramento Bee.",286));
        NEWS_ARTICLES.add(new NewsArticle("045","Our local rag is the Kenora Daily Miner and News. ",256));
    }

    /**
     * Return a random news articles.
     *
     */
    public NewsArticle getNewsArticle() {
        // pick a random article
        NewsArticle newsArticle = NEWS_ARTICLES.get(random.nextInt(NEWS_ARTICLES.size()));
        return new NewsArticle(newsArticle.getRank(),newsArticle.getTitle(),newsArticle.getNumOfPages());
    }

}
