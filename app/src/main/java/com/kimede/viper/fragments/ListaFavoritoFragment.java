package com.kimede.viper.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kimede.viper.ActivityEpisodio;
import com.kimede.viper.Models.Canal;
import com.kimede.viper.adapters.CanalAdapter;
import com.kimede.viper.adapters.RecyclerItemClickListener;
import com.kimede.viper.adapters.RecyclerItemClickListener.OnItemClickListener;
import com.kimede.viper.utils.EndlessRecyclerView;
import com.kimede.viper.utils.MarginDecoration;
import com.kimede.viper.utils.SimpleDatabaseHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaFavoritoFragment extends Fragment {
    SimpleDatabaseHelper db;
    FrameLayout frame;
    EndlessRecyclerView gridview;

    class C08571 implements OnItemClickListener {
        C08571() {
        }

        public void onItemClick(View view, int i) {
            Serializable item = ((CanalAdapter) ListaFavoritoFragment.this.gridview.getAdapter()).getItem(i);
            Intent intent = new Intent(ListaFavoritoFragment.this.getActivity(), ActivityEpisodio.class);
            intent.putExtra("anime", item);
            ListaFavoritoFragment.this.startActivity(intent);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (viewGroup == null) {
            return null;
        }
        View inflate = layoutInflater.inflate(com.kimede.viper.R.layout.grid_anime, null);
        this.gridview = (EndlessRecyclerView) inflate.findViewById(com.kimede.viper.R.id.gridview);
        this.gridview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new C08571()));
        this.frame = (FrameLayout) inflate.findViewById(com.kimede.viper.R.id.frame_loading);
        this.db = new SimpleDatabaseHelper(getActivity());
        Cursor all = this.db.getAll();
        ArrayList arrayList = new ArrayList();
        while (all.moveToNext()) {
            Canal canal = new Canal();
            canal.setId(Long.valueOf(all.getLong(all.getColumnIndex("_id"))));
            canal.setNome(all.getString(all.getColumnIndex("nome")));
            canal.setImagem(all.getString(all.getColumnIndex("imagem")));
            canal.setStatus(false);
            canal.setAno("");
            canal.setDesc("");
            canal.setCategoria("");
            arrayList.add(canal);
        }
        all.close();
        Adapter animeAdapter = new CanalAdapter(getActivity(), arrayList);
        this.gridview.addItemDecoration(new MarginDecoration(getActivity()));
        this.gridview.setHasFixedSize(false);
        this.gridview.setAdapter(animeAdapter);
        this.frame.setVisibility(View.GONE);
        return inflate;
    }
}
