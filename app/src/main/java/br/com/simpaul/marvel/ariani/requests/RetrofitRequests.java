package br.com.simpaul.marvel.ariani.requests;

import java.io.IOException;

import br.com.simpaul.marvel.ariani.BuildConfig;
import br.com.simpaul.marvel.ariani.application.NetworkModule;
import br.com.simpaul.marvel.ariani.application.SimpaulApplication;
import br.com.simpaul.marvel.ariani.models.CharacterDataWrapper;
import br.com.simpaul.marvel.ariani.models.FullCharDataWrapper;
import br.com.simpaul.marvel.ariani.retrofit.RetrofitCalls;
import retrofit2.Call;
import retrofit2.Response;

public class RetrofitRequests {

    private NetworkModule networkModule = new NetworkModule();
    private RetrofitCalls calls = networkModule.retrofit(SimpaulApplication.getDomain()).create(RetrofitCalls.class);

    public CharacterDataWrapper getCharacters(int offset, String name){
        Call<CharacterDataWrapper> call = calls.getCharacters(BuildConfig.PUBLIC_KEY, SimpaulApplication.getTimestamp(), SimpaulApplication.generateHash(), 20, offset, name);
        try {
            Response<CharacterDataWrapper> response = call.execute();
            if(response.isSuccessful()){
                return response.body();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FullCharDataWrapper getCharInfo(int charId){
        Call<FullCharDataWrapper> call = calls.getCharInfo(charId, BuildConfig.PUBLIC_KEY, SimpaulApplication.getTimestamp(), SimpaulApplication.generateHash());
        try{
            Response<FullCharDataWrapper> response = call.execute();
            if(response.isSuccessful()){
                return response.body();
            }
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
