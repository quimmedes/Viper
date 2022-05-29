package com.kimede.viper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kimede.viper.R;

import java.util.ArrayList;

public class AlphabetAdapter extends Adapter<AlphabetAdapter.AlphabetViewHolder> {
    private ArrayList<String> alphabet;
    private Context context;

    public class AlphabetViewHolder extends ViewHolder {
        TextView alphaText;

        public AlphabetViewHolder(View view) {
            super(view);
            this.alphaText = (TextView) view.findViewById(com.kimede.viper.R.id.alpha_text);
        }
    }

    public AlphabetAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.alphabet = arrayList;
    }

    public int getItemCount() {
        return this.alphabet.size();
    }

    public void onBindViewHolder(AlphabetViewHolder alphabetViewHolder, int i) {
        alphabetViewHolder.alphaText.setText(this.alphabet.get(i));
    }

    public AlphabetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AlphabetViewHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_alphabet, viewGroup, false));
    }
}
