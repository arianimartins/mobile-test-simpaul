package br.com.simpaul.marvel.ariani.models;

import java.util.ArrayList;

public class CharacterDataContainer {

    private int offset;
    private int limit;
    private int total;
    private int count;
    private ArrayList<Character> results;

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

    public ArrayList<Character> getResults() {
        return results;
    }
}