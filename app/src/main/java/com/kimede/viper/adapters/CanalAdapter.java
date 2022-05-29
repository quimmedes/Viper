package com.kimede.viper.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kimede.viper.Models.Canal;
import com.kimede.viper.R;
import com.kimede.viper.utils.CircleProgressBarDrawable;

import java.util.ArrayList;
import java.util.List;

public class CanalAdapter extends Adapter<CanalAdapter.AnimeViewHolder> {
    private Context context;
    private ArrayList<Canal> items;

    public static class AnimeViewHolder extends ViewHolder {
        public SimpleDraweeView img;
        public TextView textNome;

        public AnimeViewHolder(View view) {
            super(view);
            this.img = (SimpleDraweeView) view.findViewById(com.kimede.viper.R.id.img_shot);
            this.textNome = (TextView) view.findViewById(com.kimede.viper.R.id.txt_nome);
        }
    }

    public CanalAdapter(Context context, ArrayList<Canal> arrayList) {
        this.context = context;
        this.items = arrayList;
    }

    public Canal getItem(int i) {
        return this.items.get(i);
    }

    public int getItemCount() {
        return this.items.size();
    }

    public ArrayList<Canal> getList() {
        return this.items;
    }

    public void onBindViewHolder(AnimeViewHolder animeViewHolder, int i) {
        Canal canal = this.items.get(i);
        animeViewHolder.textNome.setText(canal.getNome());
        Uri parse = Uri.parse(canal.getImagem());
        CircleProgressBarDrawable circleProgressBarDrawable = new CircleProgressBarDrawable();
        circleProgressBarDrawable.setBackgroundColor(this.context.getResources().getColor(com.kimede.viper.R.color.white));
        circleProgressBarDrawable.setColor(this.context.getResources().getColor(com.kimede.viper.R.color.orange));
        animeViewHolder.img.setHierarchy(new GenericDraweeHierarchyBuilder(this.context.getResources()).setFadeDuration(30).setProgressBarImage(circleProgressBarDrawable).build());
        animeViewHolder.img.setImageURI(parse);
    }

    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AnimeViewHolder(LayoutInflater.from(this.context).inflate(R.layout.row_shots, viewGroup, false));
    }

    public void setItems(List<Canal> list) {
        this.items.addAll(list);
        notifyDataSetChanged();
    }
}
