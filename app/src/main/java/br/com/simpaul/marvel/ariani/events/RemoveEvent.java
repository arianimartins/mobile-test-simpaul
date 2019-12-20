package br.com.simpaul.marvel.ariani.events;

public class RemoveEvent {

    private int charId;

    public RemoveEvent(int charId){
        this.charId = charId;
    }

    public int getCharId() {
        return charId;
    }
}
