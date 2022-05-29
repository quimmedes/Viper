package com.kimede.viper.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kimede.viper.ActivityEpisodio;
import com.kimede.viper.Models.Canal;
import com.kimede.viper.R;
import com.kimede.viper.adapters.LancamentoAdapter;
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

public class RecentesEpisodio extends Fragment implements OnItemClickListener {
    public LancamentoAdapter adapter;
    public List<Canal> canals;
    public FrameLayout frame;
    public AutofitRecyclerView gridview;

    public void doBackground() {
        Call<List<Canal>> call;
        CanalService service = new Builder().baseUrl(getString(R.string.baseurl)).addConverterFactory(GsonConverterFactory.create()).build().create(CanalService.class);
                call = service.GetRecentes();
                call.enqueue(new Callback<List<Canal>>() {
            @Override
            public void onResponse(Call<List<Canal>> call, Response<List<Canal>> response)
            {
                canals = response.body();
                adapter = new LancamentoAdapter(getActivity(), canals);
                gridview.setAdapter(adapter);
                frame.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Canal>> call, Throwable t) {

            }
        });
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View v = null;
        if (viewGroup == null) {
            return null;
        }
        v = layoutInflater.inflate(R.layout.grid_anime_lista, null);
        canals = new ArrayList<>();
        frame = (FrameLayout) v.findViewById(R.id.frameload);
        gridview = (AutofitRecyclerView) v.findViewById(R.id.recycler_view);
        gridview.addItemDecoration(new MarginDecoration(getActivity()));
        gridview.setHasFixedSize(false);
        gridview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        doBackground();
        return v;
    }

    public void onItemClick(View view, int i) {
        Canal item = ((LancamentoAdapter) gridview.getAdapter()).getItem(i);
        Intent intent = new Intent(getActivity(), ActivityEpisodio.class);
        intent.putExtra("anime", item);
        startActivity(intent);
    }

}
