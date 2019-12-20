package br.com.simpaul.marvel.ariani.models;

public class CharacterDataWrapper {

    private String copyright;
    private String attributionText;
    private CharacterDataContainer data;

    public CharacterDataContainer getData() {
        return data;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public String getCopyright() {
        return copyright;
    }
}