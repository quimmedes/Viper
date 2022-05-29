package com.kimede.viper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kimede.viper.Models.Categoria;
import com.kimede.viper.R;

import java.util.List;

public class CategoriasAdapter extends Adapter<CategoriasAdapter.CategoriaViewHolder> {
    private List<Categoria> alphabet;
    private Context context;

    public class CategoriaViewHolder extends ViewHolder {
        TextView alphaText;

        public CategoriaViewHolder(View view) {
            super(view);
            this.alphaText = (TextView) view.findViewById(com.kimede.viper.R.id.tituloOrdemTxt);
        }
    }

    public CategoriasAdapter(Context context, List<Categoria> list) {
        this.context = context;
        this.alphabet = list;
    }

    public Categoria getItem(int i) {
        return this.alphabet.get(i);
    }

    public int getItemCount() {
        return this.alphabet.size();
    }

    public void onBindViewHolder(CategoriaViewHolder categoriaViewHolder, int i) {
        categoriaViewHolder.alphaText.setText(this.alphabet.get(i).getNome());
    }

    public CategoriaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CategoriaViewHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_ordem, viewGroup, false));
    }
}
