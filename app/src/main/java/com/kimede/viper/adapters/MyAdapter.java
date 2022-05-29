package com.kimede.viper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kimede.viper.Models.Link;
import com.kimede.viper.R;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    List<Link> link;
    LayoutInflater inflter;

    public MyAdapter(Context applicationContext, List<Link > link) {

        this.context = applicationContext;
        this.link = link;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return link.size();
    }

    @Override
    public Object getItem(int i) {
        return link.get(i);
    }

    public String getLink(int id){
        return  link.get(id).getEndereco();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_item, null);
        TextView names = (TextView) view.findViewById(R.id.tvLanguage);
        names.setText(link.get(i).getNome());
        return view;
    }
}