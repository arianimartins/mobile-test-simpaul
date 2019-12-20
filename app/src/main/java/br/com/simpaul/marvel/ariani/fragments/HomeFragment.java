package br.com.simpaul.marvel.ariani.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.simpaul.marvel.ariani.R;
import br.com.simpaul.marvel.ariani.adapters.CharactersListAdapter;
import br.com.simpaul.marvel.ariani.application.NetworkModule;
import br.com.simpaul.marvel.ariani.application.SimpaulApplication;
import br.com.simpaul.marvel.ariani.events.RemoveEvent;
import br.com.simpaul.marvel.ariani.events.SaveEvent;
import br.com.simpaul.marvel.ariani.helper.EndlessRecyclerViewScrollListener;
import br.com.simpaul.marvel.ariani.models.Character;
import br.com.simpaul.marvel.ariani.models.CharacterDataWrapper;
import br.com.simpaul.marvel.ariani.tasks.CharactersListTask;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private EndlessRecyclerViewScrollListener scrollListener;
    @BindView(R.id.rv_chars) RecyclerView rvCharacters;
    @BindView(R.id.txt_copy) TextView copyright;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @BindView(R.id.search) SearchView searchView;

    private Context context;
    private CharactersListAdapter listAdapter;
    private ArrayList<Character> charactersList;
    private ArrayList<Character> tempList;

    boolean isLoading = false;
    boolean first = true;
    int total;
    private boolean filter = false;
    private boolean search = true;
    private String name;
    private int offset;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        init();
        configSearch();

        if(NetworkModule.hasNetwork()){
            loadAll();
        }else{
            Snackbar.make(getActivity().findViewById(android.R.id.content),R.string.error_connection, Snackbar.LENGTH_LONG).show();
        }

        return view;
    }

    private void init(){
        charactersList = new ArrayList<>();
        listAdapter = new CharactersListAdapter(SimpaulApplication.getAppContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SimpaulApplication.getAppContext());
        rvCharacters.setLayoutManager(linearLayoutManager);
        rvCharacters.setAdapter(listAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(search){
                    progressBar.setVisibility(View.VISIBLE);
                    offset = charactersList.size();
                    loadAll();
                }
            }
        };
        rvCharacters.addOnScrollListener(scrollListener);

    }

    private void loadAll(){
        new CharactersListTask(new CharactersListTask.AsyncResponse() {
            @Override
            public void processFinish(CharacterDataWrapper response) {
                progressBar.setVisibility(View.GONE);

                if(response != null && response.getData().getResults().size() > 0){
                    if(first){
                        first = false;
                        total = response.getData().getTotal();
                        copyright.setText(response.getAttributionText());
                    }

                    charactersList.addAll(response.getData().getResults());
                    listAdapter.setItems(charactersList);
                    listAdapter.notifyDataSetChanged();
                }

                isLoading = false;
                if(charactersList.size() >= total){
                    search = false;
                }
            }
            }, offset,name).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void configSearch(){
        searchView.setIconifiedByDefault(false);
        searchView.onActionViewExpanded();

        if(!searchView.isFocused()) {
            searchView.clearFocus();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search = true;
                filter = true;

                charactersList.clear();
                listAdapter.notifyDataSetChanged();
                scrollListener.resetState();
                offset = 0;
                name = s;

                loadAll();

                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.isEmpty()){
                    search = true;
                    charactersList.clear();
                    listAdapter.notifyDataSetChanged();
                    scrollListener.resetState();
                    offset = 0;
                    name = null;
                    loadAll();
                }
                return false;
            }
        });
    }

    @Subscribe
    public void onSaveEvent(final SaveEvent event){
        int i = listAdapter.getItemPosition(event.getCharId());
        charactersList.get(i).setSaved(true);
        listAdapter.notifyItemChanged(i);
    }

    @Subscribe
    public void onRemoveEvent(final RemoveEvent event){
        int i = listAdapter.getItemPosition(event.getCharId());
        charactersList.get(i).setSaved(false);
        listAdapter.notifyItemChanged(i);
    }

}
