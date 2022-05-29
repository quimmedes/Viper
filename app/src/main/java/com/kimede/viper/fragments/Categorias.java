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
import com.kimede.viper.interfaces.CanalService;
import com.kimede.viper.utils.AutofitRecyclerView;
import com.kimede.viper.utils.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class Categorias extends Fragment implements OnItemClickListener  {
    List<Categoria> categoria;
    CategoriasAdapter categoriasAdapter;
    AutofitRecyclerView gridview;

        public void onItemClick(View view, int i) {
            Categoria item = ((CategoriasAdapter) gridview.getAdapter()).getItem(i);
            Fragment categoriaFragment = new CategoriaFragment();
            Bundle bundle = new Bundle();
            bundle.putString("categoria", item.getNome());
            categoriaFragment.setArguments(bundle);
            ((MyActivity) getActivity()).switchContent(categoriaFragment);

        }


    public Categorias() {
        this.categoria = new ArrayList();
    }

    public void doBackground() {
        new Builder().baseUrl(getResources().getString(R.string.baseurl)).
                addConverterFactory(GsonConverterFactory.create()).build()
                .create(CanalService.class).GetCategoria().enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                categoria = (List) response.body();
                categoriasAdapter = new CategoriasAdapter(getActivity(), categoria);
                gridview.setAdapter(categoriasAdapter);
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (viewGroup == null) {
            return null;
        }
        View inflate = layoutInflater.inflate(R.layout.categoria_gridview, null);
        this.gridview = (AutofitRecyclerView) inflate.findViewById(R.id.categoria_grid);
        this.categoriasAdapter = new CategoriasAdapter(getActivity(), this.categoria);
        this.gridview.addItemDecoration(new MarginDecoration(getActivity()));
        this.gridview.setHasFixedSize(false);
        this.gridview.setAdapter(this.categoriasAdapter);
        this.gridview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        doBackground();
        return inflate;
    }
}
