package br.com.simpaul.marvel.ariani.retrofit;

import java.util.ArrayList;

import br.com.simpaul.marvel.ariani.models.CharacterDataWrapper;
import br.com.simpaul.marvel.ariani.models.FullCharDataWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitCalls {

    @GET("characters")
    Call<CharacterDataWrapper> getCharacters(@Query("apikey") String publickey,
                                             @Query("ts") String timestamp,
                                             @Query("hash") String hash,
                                             @Query("limit") int limit,
                                             @Query("offset") int offset,
                                             @Query("nameStartsWith") String name);


    @GET("characters/{id}")
    Call<FullCharDataWrapper> getCharInfo(@Path("id") int charId,
                                          @Query("apikey") String publickey,
                                          @Query("ts") String timestamp,
                                          @Query("hash") String hash);

}