package br.com.simpaul.marvel.ariani.models;

public class FullCharacter extends Character{

    private String description;
    private ComicList comics;
    private StoryList stories;
    private EventList events;
    private SeriesList series;

    public String getDescription() {
        return description;
    }

    public ComicList getComics() {
        return comics;
    }

    public StoryList getStories() {
        return stories;
    }

    public EventList getEvents() {
        return events;
    }

    public SeriesList getSeries() {
        return series;
    }
}

/*Character {
        id (int, optional): The unique ID of the character resource.,
        name (string, optional): The name of the character.,
        thumbnail (Image, optional): The representative image for this character.,

        description (string, optional): A short bio or description of the character.,
        comics (ComicList, optional): A resource list containing comics which feature this character.,
        stories (StoryList, optional): A resource list of stories in which this character appears.,
        events (EventList, optional): A resource list of events in which this character appears.,
        series (SeriesList, optional): A resource list of series in which this character appears.

        modified (Date, optional): The date the resource was most recently modified.,
        resourceURI (string, optional): The canonical URL identifier for this resource.,
        urls (Array[Url], optional): A set of public web site URLs for the resource.,


        } */