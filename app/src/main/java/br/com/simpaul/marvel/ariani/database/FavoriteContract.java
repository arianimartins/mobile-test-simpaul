package br.com.simpaul.marvel.ariani.database;

import android.provider.BaseColumns;

public final class FavoriteContract {

    private FavoriteContract() {}

    public static class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "bookmarks";
        public static final String COLUMN_NAME_CHAR_ID = "char_id";
    }

}
