package br.com.simpaul.marvel.ariani.tasks;

import android.os.AsyncTask;

import br.com.simpaul.marvel.ariani.models.CharacterDataWrapper;
import br.com.simpaul.marvel.ariani.requests.RetrofitRequests;

public class CharactersListTask  extends AsyncTask<Void, Void, CharacterDataWrapper> {

    public AsyncResponse delegate;
    private int offset;
    private String name;

    public interface AsyncResponse{
        void processFinish(CharacterDataWrapper response);
    }

    public CharactersListTask(AsyncResponse delegate, int offset, String name) {
        this.delegate = delegate;
        this.offset = offset;
        this.name = name;
    }

    @Override
    protected CharacterDataWrapper doInBackground(Void... voids) {
        return new RetrofitRequests().getCharacters(offset, name);
    }

    @Override
    protected void onPostExecute(CharacterDataWrapper characterDataWrapper) {
        delegate.processFinish(characterDataWrapper);
    }
}
