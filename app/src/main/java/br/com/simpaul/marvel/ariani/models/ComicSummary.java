package br.com.simpaul.marvel.ariani.models;

public class ComicSummary {

    private String resourceURI;
    private String name;

    public String getName() {
        return name;
    }

    public String getResourceURI() {
        return resourceURI;
    }
}

/*
ComicSummary {
resourceURI (string, optional): The path to the individual comic resource.,
name (string, optional): The canonical name of the comic.
}
 */