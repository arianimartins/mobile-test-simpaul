package br.com.simpaul.marvel.ariani.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.simpaul.marvel.ariani.R;
import br.com.simpaul.marvel.ariani.activities.CharacterDetailActivity;
import br.com.simpaul.marvel.ariani.database.DbHelper;
import br.com.simpaul.marvel.ariani.helper.Enums;
import br.com.simpaul.marvel.ariani.helper.ImageHelper;
import br.com.simpaul.marvel.ariani.models.Character;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CharactersListAdapter extends RecyclerView.Adapter {

    private ArrayList<Character> items;
    private Context context;


    public CharactersListAdapter(Context context){
        this.context = context;
    }
    public void setItems(ArrayList<Character> newItems){
        this.items = newItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_list_char, parent, false);
        return new CharactersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CharactersHolder h = (CharactersHolder) holder;
        Character character = items.get(position);

        if(DbHelper.itemExists(context,character.getId())){
            h.name.setTextColor(context.getResources().getColor(R.color.colorAccent));
            character.setSaved(true);
            h.favicon.setVisibility(View.VISIBLE);
        }else{
            h.name.setTextColor(context.getResources().getColor(R.color.textColor));
            character.setSaved(false);
            h.favicon.setVisibility(View.GONE);
        }

        h.name.setText(character.getName());
        ImageHelper.loadImage(character.getThumbnail().getPath()+"/standard_medium."+character.getThumbnail().getExtension(), h.thumbnail);
    }


    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public int getItemPosition(int id) {
        for (int pos = 0; pos < items.size(); pos++){
            if (items.get(pos).getId() == id){
                return pos;
            }
        }
        return 0;
    }

    public class CharactersHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_char) ImageView thumbnail;
        @BindView(R.id.txt_chat_name) TextView name;
        @BindView(R.id.fav_icon) ImageView favicon;

        public CharactersHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Character c = items.get(getAdapterPosition());
                    Intent intent = new Intent(context, CharacterDetailActivity.class);
                    intent.putExtra(Enums.Items.ID.name(), c.getId());
                    intent.putExtra(Enums.Items.NAME.name(), c.getName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
