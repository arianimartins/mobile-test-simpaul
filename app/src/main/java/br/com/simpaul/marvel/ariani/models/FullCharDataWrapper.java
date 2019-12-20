package br.com.simpaul.marvel.ariani.models;

public class FullCharDataWrapper {

    private String copyright;
    private String attributionText;
    private FullCharDataContainer data;

    public FullCharDataContainer getData() {
        return data;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public String getCopyright() {
        return copyright;
    }
}