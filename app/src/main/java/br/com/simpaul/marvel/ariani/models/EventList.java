package br.com.simpaul.marvel.ariani.models;

import java.util.ArrayList;

public class EventList {

    private int available;
    private int returned;
    private ArrayList<EventSummary> items;

    public int getAvailable() {
        return available;
    }

    public int getReturned() {
        return returned;
    }

    public ArrayList<EventSummary> getItems() {
        return items;
    }
}

/*
EventList {
available (int, optional): The number of total available events in this list. Will always be greater than or equal to the "returned" value.,
returned (int, optional): The number of events returned in this collection (up to 20).,
collectionURI (string, optional): The path to the full list of events in this collection.,
items (Array[EventSummary], optional): The list of returned events in this collection.
}
 */
