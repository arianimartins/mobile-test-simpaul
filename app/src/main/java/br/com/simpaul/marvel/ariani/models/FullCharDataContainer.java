package br.com.simpaul.marvel.ariani.models;

import java.util.ArrayList;

public class FullCharDataContainer {

    private int offset;
    private int limit;
    private int total;
    private int count;
    private ArrayList<FullCharacter> results;

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<FullCharacter> getResults() {
        return results;
    }
}