package com.kimede.viper.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.kimede.viper.Models.Canal;
import com.kimede.viper.R;
import com.kimede.viper.adapters.CanalAdapter.AnimeViewHolder;
import com.kimede.viper.utils.CircleProgressBarDrawable;

import java.util.List;

public class LancamentoAdapter extends Adapter<AnimeViewHolder> {
    private Context context;
    private List<Canal> items;

    public LancamentoAdapter(Context context, List<Canal> list) {
        this.context = context;
        this.items = list;
    }

    public Canal getItem(int i) {
        return this.items.get(i);
    }

    public int getItemCount() {
        return this.items.size();
    }


    public void onBindViewHolder(AnimeViewHolder animeViewHolder, int i) {
        Canal canal = this.items.get(i);
        animeViewHolder.textNome.setText(canal.getNome());
        Uri parse = Uri.parse(canal.getImagem());
        CircleProgressBarDrawable circleProgressBarDrawable = new CircleProgressBarDrawable();
        circleProgressBarDrawable.setBackgroundColor(this.context.getResources().getColor(R.color.white));
        circleProgressBarDrawable.setColor(this.context.getResources().getColor(R.color.orange));
        animeViewHolder.img.setHierarchy(new GenericDraweeHierarchyBuilder(this.context.getResources()).setFadeDuration(30).setProgressBarImage(circleProgressBarDrawable).build());
        animeViewHolder.img.setImageURI(parse);
        animeViewHolder.textNome.setTextSize(12);
    }

    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AnimeViewHolder(LayoutInflater.from(this.context).inflate(R.layout.row_shots, viewGroup, false));
    }
}
