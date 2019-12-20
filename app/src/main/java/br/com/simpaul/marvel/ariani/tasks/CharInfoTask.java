package br.com.simpaul.marvel.ariani.tasks;

import android.os.AsyncTask;

import br.com.simpaul.marvel.ariani.models.CharacterDataWrapper;
import br.com.simpaul.marvel.ariani.models.FullCharDataWrapper;
import br.com.simpaul.marvel.ariani.requests.RetrofitRequests;

public class CharInfoTask extends AsyncTask<Void, Void, FullCharDataWrapper> {

    private int charId;
    public AsyncResponse delegate;

    public interface AsyncResponse{
        void processFinish(FullCharDataWrapper response);
    }

    public CharInfoTask(int charId, AsyncResponse delegate) {
        this.charId = charId;
        this.delegate = delegate;
    }

    @Override
    protected FullCharDataWrapper doInBackground(Void... voids) {
        return new RetrofitRequests().getCharInfo(charId);
    }

    @Override
    protected void onPostExecute(FullCharDataWrapper characterDataWrapper) {
        delegate.processFinish(characterDataWrapper);
    }
}
