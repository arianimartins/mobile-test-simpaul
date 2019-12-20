package br.com.simpaul.marvel.ariani.events;

public class SaveEvent {

    private int charId;

    public SaveEvent(int charId){
        this.charId = charId;
    }

    public int getCharId() {
        return charId;
    }
}
