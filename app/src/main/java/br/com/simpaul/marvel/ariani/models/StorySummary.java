package br.com.simpaul.marvel.ariani.models;

public class StorySummary {

    private String type;
    private String name;
    private String resourceURI;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getResourceURI() {
        return resourceURI;
    }
}
/*
StorySummary {
resourceURI (string, optional): The path to the individual story resource.,
name (string, optional): The canonical name of the story.,
type (string, optional): The type of the story (interior or cover).
}
 */