package br.com.simpaul.marvel.ariani.models;

import java.util.ArrayList;

public class ComicList {

    private int available;
    private int returned;
    private ArrayList<ComicSummary> items;

    public ArrayList<ComicSummary> getItems() {
        return items;
    }

    public int getAvailable() {
        return available;
    }

    public int getReturned() {
        return returned;
    }
}

/*ComicList {
        available (int, optional): The number of total available issues in this list. Will always be greater than or equal to the "returned" value.,
        returned (int, optional): The number of issues returned in this collection (up to 20).,
        collectionURI (string, optional): The path to the full list of issues in this collection.,
        items (Array[ComicSummary], optional): The list of returned issues in this collection.
        }*/
