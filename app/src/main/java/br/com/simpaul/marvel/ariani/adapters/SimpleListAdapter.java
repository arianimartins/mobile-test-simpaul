package br.com.simpaul.marvel.ariani.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.simpaul.marvel.ariani.R;
import br.com.simpaul.marvel.ariani.activities.CharacterDetailActivity;
import br.com.simpaul.marvel.ariani.helper.Enums;
import br.com.simpaul.marvel.ariani.helper.ImageHelper;
import br.com.simpaul.marvel.ariani.models.Character;
import br.com.simpaul.marvel.ariani.models.CharacterDataWrapper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleListAdapter extends RecyclerView.Adapter {

    private ArrayList<String> items;
    private Context context;

    public SimpleListAdapter(Context context){
        this.context = context;
    }

    public void setItems(ArrayList<String> newItems){
        this.items = newItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_list_simple, parent, false);
        return new ItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemsHolder h = (ItemsHolder) holder;
        String value = items.get(position);

        h.name.setText(value);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class ItemsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_item) TextView name;

        public ItemsHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
