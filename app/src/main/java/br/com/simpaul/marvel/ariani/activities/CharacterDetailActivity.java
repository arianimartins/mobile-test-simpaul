package br.com.simpaul.marvel.ariani.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.simpaul.marvel.ariani.R;
import br.com.simpaul.marvel.ariani.adapters.SimpleListAdapter;
import br.com.simpaul.marvel.ariani.application.NetworkModule;
import br.com.simpaul.marvel.ariani.database.DbHelper;
import br.com.simpaul.marvel.ariani.database.FavoriteContract;
import br.com.simpaul.marvel.ariani.events.RemoveEvent;
import br.com.simpaul.marvel.ariani.events.SaveEvent;
import br.com.simpaul.marvel.ariani.helper.EndlessRecyclerViewScrollListener;
import br.com.simpaul.marvel.ariani.helper.Enums;
import br.com.simpaul.marvel.ariani.helper.ImageHelper;
import br.com.simpaul.marvel.ariani.models.CharacterDataWrapper;
import br.com.simpaul.marvel.ariani.models.ComicList;
import br.com.simpaul.marvel.ariani.models.ComicSummary;
import br.com.simpaul.marvel.ariani.models.EventSummary;
import br.com.simpaul.marvel.ariani.models.FullCharDataWrapper;
import br.com.simpaul.marvel.ariani.models.FullCharacter;
import br.com.simpaul.marvel.ariani.models.Image;
import br.com.simpaul.marvel.ariani.models.SeriesSummary;
import br.com.simpaul.marvel.ariani.models.StorySummary;
import br.com.simpaul.marvel.ariani.tasks.CharInfoTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.AlphaModifier;
import com.plattysoft.leonids.modifiers.ScaleModifier;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class CharacterDetailActivity extends AppCompatActivity implements NestedScrollView.OnScrollChangeListener {

    @BindView(R.id.scroll_detail) NestedScrollView scrollView;
    @BindView(R.id.cover_img) ImageView coverImage;
    @BindView(R.id.fab_bookmark) FloatingActionButton fabFav;

    @BindView(R.id.txt_char_desc) TextView chatDescription;
    @BindView(R.id.msg_no_desc) TextView noDesc;

    @BindView(R.id.comics_list) RecyclerView comicsListView;
    @BindView(R.id.msg_no_comics) TextView noComics;
    private ArrayList<String> comicsList;
    private SimpleListAdapter comicsAdapter;


    @BindView(R.id.events_list) RecyclerView eventsListView;
    @BindView(R.id.msg_no_events) TextView noEvents;
    private ArrayList<String> eventsList;
    private SimpleListAdapter eventsAdapter;

    @BindView(R.id.series_list) RecyclerView seriesListView;
    @BindView(R.id.msg_no_series) TextView noSeries;
    private ArrayList<String> seriesList;
    private SimpleListAdapter seriesAdapter;

    @BindView(R.id.stories_list) RecyclerView storiesListView;
    @BindView(R.id.msg_no_stories) TextView noStories;
    private ArrayList<String> storiesList;
    private SimpleListAdapter storiesAdapter;

    private int charId;
    private String charName;

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null){
            charId = intent.hasExtra(Enums.Items.ID.name()) ? intent.getIntExtra(Enums.Items.ID.name(),0) : 0;
            charName = intent.hasExtra(Enums.Items.NAME.name()) ? intent.getStringExtra(Enums.Items.NAME.name()) : "";
        }

        configToolbar();
        init();

        if(NetworkModule.hasNetwork() && charId != 0){
            loadCharacterInfo();
        }else{
            Snackbar.make(fabFav,R.string.error_connection, Snackbar.LENGTH_LONG).show();
        }
    }

    private void configToolbar(){
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle(charName);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            supportFinishAfterTransition();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }


    private void init(){
        scrollView.setOnScrollChangeListener(this);

        comicsList = new ArrayList<>();
        comicsAdapter = new SimpleListAdapter(this);
        comicsListView.setLayoutManager(new LinearLayoutManager(this));
        comicsListView.setAdapter(comicsAdapter);

        eventsList = new ArrayList<>();
        eventsAdapter = new SimpleListAdapter(this);
        eventsListView.setLayoutManager(new LinearLayoutManager(this));
        eventsListView.setAdapter(eventsAdapter);

        seriesList = new ArrayList<>();
        seriesAdapter = new SimpleListAdapter(this);
        seriesListView.setLayoutManager(new LinearLayoutManager(this));
        seriesListView.setAdapter(seriesAdapter);

        storiesList = new ArrayList<>();
        storiesAdapter = new SimpleListAdapter(this);
        storiesListView.setLayoutManager(new LinearLayoutManager(this));
        storiesListView.setAdapter(storiesAdapter);

        toggleFabButton(DbHelper.itemExists(this,charId));
    }

    private void toggleFabButton(boolean saved){
        if(saved){
            fabFav.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
            fabFav.invalidate();
            fabFav.requestLayout();
        }else{
            fabFav.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            fabFav.invalidate();
            fabFav.requestLayout();
        }
    }

    private void loadCharacterInfo(){
        new CharInfoTask(charId, new CharInfoTask.AsyncResponse() {
            @Override
            public void processFinish(FullCharDataWrapper response) {
                if(response != null){
                    FullCharacter character = response.getData().getResults().get(0);

                    Image thumbnail = character.getThumbnail();
                    ImageHelper.loadImage(thumbnail.getPath()+ "/standard_fantastic."+thumbnail.getExtension(),coverImage);

                    if(!character.getDescription().equals("") && character.getDescription() != null){
                        noDesc.setVisibility(View.GONE);
                        chatDescription.setVisibility(View.VISIBLE);
                        chatDescription.setText(character.getDescription());
                    }else{
                        chatDescription.setVisibility(View.GONE);
                        noDesc.setVisibility(View.VISIBLE);
                    }

                    if(character.getComics().getReturned() > 0){
                        noComics.setVisibility(View.GONE);
                        ArrayList<ComicSummary> items = character.getComics().getItems();
                        for(ComicSummary item : items){
                            comicsList.add(item.getName());
                        }
                    }else{
                        noComics.setVisibility(View.VISIBLE);
                    }
                    comicsAdapter.setItems(comicsList);
                    comicsAdapter.notifyDataSetChanged();


                    if(character.getEvents().getReturned() > 0){
                        noEvents.setVisibility(View.GONE);
                        ArrayList<EventSummary> items = character.getEvents().getItems();
                        for(EventSummary item : items){
                            eventsList.add(item.getName());
                        }
                    }else{
                        noEvents.setVisibility(View.VISIBLE);
                    }
                    eventsAdapter.setItems(eventsList);
                    eventsAdapter.notifyDataSetChanged();


                    if(character.getSeries().getReturned() > 0){
                        noSeries.setVisibility(View.GONE);
                        ArrayList<SeriesSummary> items = character.getSeries().getItems();
                        for(SeriesSummary item : items){
                            seriesList.add(item.getName());
                        }
                    }else{
                        noSeries.setVisibility(View.VISIBLE);
                    }
                    seriesAdapter.setItems(seriesList);
                    seriesAdapter.notifyDataSetChanged();


                    if(character.getStories().getReturned() > 0){
                        noStories.setVisibility(View.GONE);
                        ArrayList<StorySummary> items = character.getStories().getItems();
                        for(StorySummary item : items){
                            storiesList.add(item.getName());
                        }
                    }else{
                        noStories.setVisibility(View.VISIBLE);
                    }
                    storiesAdapter.setItems(storiesList);
                    storiesAdapter.notifyDataSetChanged();
                }
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Subscribe
    public void onSaveEvent(final SaveEvent event){
        toggleFabButton(true);
    }

    @Subscribe
    public void onRemoveEvent(final RemoveEvent event){
        toggleFabButton(false);
    }

    @OnClick(R.id.fab_bookmark)
    public void saveItem(){
        String columnName = FavoriteContract.FavoriteEntry.COLUMN_NAME_CHAR_ID;

        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        if(!DbHelper.itemExists(this,charId)){
            new ParticleSystem(this, 5, R.drawable.capt_small, 1000)
                    .setSpeedByComponentsRange(-0.07f, 0.07f, -0.18f, -0.24f)
                    .setAcceleration(0.00003f, 30)
                    .setInitialRotationRange(0, 360)
                    .addModifier(new AlphaModifier(255, 0, 1000, 3000))
                    .addModifier(new ScaleModifier(0.5f, 2f, 0, 1000))
                    //.setSpeedModuleAndAngleRange(0.2f,0.5f,240,270)
                    //.setFadeOut(200, new AccelerateInterpolator())
                    .oneShot(fabFav, 5);

            SQLiteDatabase db = new DbHelper(this).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FavoriteContract.FavoriteEntry.COLUMN_NAME_CHAR_ID, charId);
            long newRowId = db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
            db.close();

            EventBus.getDefault().post(new SaveEvent(charId));

        }else{
            SQLiteDatabase db = new DbHelper(this).getWritableDatabase();
            String query = columnName + " = " + charId;
            db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, query, null);
            db.close();

            EventBus.getDefault().post(new RemoveEvent(charId));
        }
    }


    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(scrollY > oldScrollY && fabFav.getVisibility() == View.VISIBLE){
            fabFav.hide();

        }else if(scrollY < oldScrollY && fabFav.getVisibility() != View.VISIBLE){
            fabFav.show();
        }


        /*if(v.getChildAt(v.getChildCount() - 1) != null){
            if((scrollY >= (v.getChildAt(v.getChildCount() -1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldY) {

                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(viewListPosts.getLayoutManager());
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                lastVisible = layoutManager.findLastVisibleItemPosition();

                if (search) {
                    if ((visibleItemCount + lastVisible) >= totalItemCount) {
                        if (listPosts.size() > 0) {
                            long lastIdA = listPosts.get(listPosts.size() - 1).getPostId();
                            if (lastIdA != lastIdB) {
                                showFeedLoading();
                                lastIdB = lastIdA;
                                getPosts(lastIdA);
                            }
                        }
                    }
                }
            }
        }*/
    }
}
