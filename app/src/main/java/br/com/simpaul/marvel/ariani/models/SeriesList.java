package br.com.simpaul.marvel.ariani.models;

import java.util.ArrayList;

public class SeriesList {
    private int available;
    private int returned;
    private ArrayList<SeriesSummary> items;

    public int getAvailable() {
        return available;
    }

    public int getReturned() {
        return returned;
    }

    public ArrayList<SeriesSummary> getItems() {
        return items;
    }
}

/*
SeriesList {
available (int, optional): The number of total available series in this list. Will always be greater than or equal to the "returned" value.,
returned (int, optional): The number of series returned in this collection (up to 20).,
collectionURI (string, optional): The path to the full list of series in this collection.,
items (Array[SeriesSummary], optional): The list of returned series in this collection.
}
 */