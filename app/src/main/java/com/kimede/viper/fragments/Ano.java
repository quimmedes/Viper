package com.kimede.viper.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimede.viper.Models.Categoria;
import com.kimede.viper.MyActivity;
import com.kimede.viper.R;
import com.kimede.viper.adapters.CategoriasAdapter;
import com.kimede.viper.adapters.RecyclerItemClickListener;
import com.kimede.viper.adapters.RecyclerItemClickListener.OnItemClickListener;
import com.kimede.viper.utils.AutofitRecyclerView;
import com.kimede.viper.utils.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

public class Ano extends Fragment implements OnItemClickListener {
    List<Categoria> categorias = new ArrayList();;
    CategoriasAdapter categoriasAdapter;
    AutofitRecyclerView gridview;

        public void onItemClick(View view, int i) {
            Categoria item = ((CategoriasAdapter) Ano.this.gridview.getAdapter()).getItem(i);
            Fragment anoFragment = new AnoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ano", item.getNome());
            anoFragment.setArguments(bundle);
            ((MyActivity) Ano.this.getActivity()).switchContent(anoFragment);
        }


    public void doBackground() {
        this.categorias = new ArrayList();
        this.categorias.add(new Categoria("2018"));
        this.categorias.add(new Categoria("2017"));
        this.categorias.add(new Categoria("2016"));
        this.categorias.add(new Categoria("2015"));
        this.categorias.add(new Categoria("2014"));
        this.categorias.add(new Categoria("2013"));
        this.categorias.add(new Categoria("2012"));
        this.categorias.add(new Categoria("2011"));
        this.categorias.add(new Categoria("2010"));
        this.categorias.add(new Categoria("2009"));
        this.categorias.add(new Categoria("2008"));
        this.categorias.add(new Categoria("2007"));
        this.categorias.add(new Categoria("2006"));
        this.categorias.add(new Categoria("2005"));
        this.categorias.add(new Categoria("2004"));
        this.categorias.add(new Categoria("2003"));
        this.categorias.add(new Categoria("2002"));
        this.categorias.add(new Categoria("2001"));
        this.categorias.add(new Categoria("2000"));
        this.categorias.add(new Categoria("1999"));
        this.categorias.add(new Categoria("1998"));
        this.categorias.add(new Categoria("1997"));
        this.categorias.add(new Categoria("1996"));
        this.categorias.add(new Categoria("1995"));
        this.categorias.add(new Categoria("1994"));
        this.categorias.add(new Categoria("1993"));
        this.categorias.add(new Categoria("1992"));
        this.categorias.add(new Categoria("1991"));
        this.categorias.add(new Categoria("1990"));
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (viewGroup == null) {
            return null;
        }
        View inflate = layoutInflater.inflate(R.layout.categoria_gridview, null);
        doBackground();
        this.gridview = (AutofitRecyclerView) inflate.findViewById(R.id.categoria_grid);
        this.categoriasAdapter = new CategoriasAdapter(getActivity(), this.categorias);
        this.gridview.addItemDecoration(new MarginDecoration(getActivity(), R.dimen.dim_2));
        this.gridview.setHasFixedSize(true);
        this.gridview.setAdapter(this.categoriasAdapter);
        this.gridview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        return inflate;
    }
}
