package br.com.simpaul.marvel.ariani.models;

import java.util.ArrayList;

public class StoryList {

    private int available;
    private int returned;
    private ArrayList<StorySummary> items;

    public int getReturned() {
        return returned;
    }

    public int getAvailable() {
        return available;
    }

    public ArrayList<StorySummary> getItems() {
        return items;
    }
}

/*
StoryList {
available (int, optional): The number of total available stories in this list. Will always be greater than or equal to the "returned" value.,
returned (int, optional): The number of stories returned in this collection (up to 20).,
collectionURI (string, optional): The path to the full list of stories in this collection.,
items (Array[StorySummary], optional): The list of returned stories in this collection.
}
 */
