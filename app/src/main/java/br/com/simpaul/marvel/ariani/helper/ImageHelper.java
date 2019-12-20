package br.com.simpaul.marvel.ariani.helper;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageHelper {

    public static void loadImage(String url, final ImageView view){
        Picasso.get().load(url).into(view);
    }

}
