package com.kimede.viper.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kimede.viper.ActivityEpisodio;
import com.kimede.viper.Models.Canal;
import com.kimede.viper.Models.Odata;
import com.kimede.viper.R;
import com.kimede.viper.adapters.CanalAdapter;
import com.kimede.viper.adapters.RecyclerItemClickListener;
import com.kimede.viper.adapters.RecyclerItemClickListener.OnItemClickListener;
import com.kimede.viper.interfaces.CanalService;
import com.kimede.viper.utils.EndlessRecyclerView;
import com.kimede.viper.utils.EndlessRecyclerView.Pager;
import com.kimede.viper.utils.MarginDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriaFragment extends Fragment implements Pager, OnItemClickListener {
    private static final long DELAY = 1000;
    CanalAdapter adapter;
    ArrayList<Canal> canals = new ArrayList();;
    public String categoria = null;
    FrameLayout frame;
    EndlessRecyclerView gridview;
    private final Handler handler = new Handler();
    private boolean loading = false;
    public int skip = 0;
    public int total = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        if (container != null) {
            this.skip = 0;
            this.total = 0;
            this.canals = new ArrayList();
            this.categoria = getArguments().getString("categoria");
            v = inflater.inflate(R.layout.grid_anime, null);
            this.frame = (FrameLayout) v.findViewById(R.id.frame_loading);
            this.gridview = (EndlessRecyclerView) v.findViewById(R.id.gridview);
            this.adapter = new CanalAdapter(getActivity(), this.canals);
            this.gridview.addItemDecoration(new MarginDecoration(getActivity()));
            this.gridview.setHasFixedSize(false);
            this.gridview.setProgressView(R.layout.item_progress);
            this.gridview.setAdapter(this.adapter);
            this.gridview.setPager(this);
            this.gridview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
            if (savedInstanceState != null) {
                this.skip = savedInstanceState.getInt("skip");
                this.total = savedInstanceState.getInt("total");
                this.canals = (ArrayList) savedInstanceState.getSerializable("animes");
                this.adapter = new CanalAdapter(getActivity(), this.canals);
                this.gridview.setAdapter(this.adapter);
                frame.setVisibility(View.GONE);
                this.gridview.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable("position"));
            } else {
                doBackground();
            }
        }
        return v;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("skip", this.skip);
        bundle.putInt("total", this.total);
        bundle.putString("categoria", this.categoria);
        bundle.putSerializable("animes", ((CanalAdapter) this.gridview.getAdapter()).getList());
        bundle.putParcelable("position", this.gridview.getLayoutManager().onSaveInstanceState());
    }


        public void onItemClick(View view, int i) {
            Canal item = ((CanalAdapter) CategoriaFragment.this.gridview.getAdapter()).getItem(i);
            Intent intent = new Intent(CategoriaFragment.this.getActivity(), ActivityEpisodio.class);
            intent.putExtra("anime", item);
            startActivity(intent);
        }


    public void doBackground() {

        Call<Odata> call;
        CanalService service = new Retrofit.Builder().baseUrl(getResources().getString(R.string.baseurl)).addConverterFactory(GsonConverterFactory.create()).build().create(CanalService.class);

            call = service.GetOdataAlphabet("substringof('"+categoria.replace("'","''")+"',Categoria)", "Id,Nome,Imagem,Categoria", "Nome", String.valueOf(this.skip), "allpages");

        call.enqueue(new Callback<Odata>() {
            @Override
            public void onResponse(Call<Odata> call, Response<Odata> response) {


                if (((Odata) response.body()).getOdataCount() != null) {
                    total = Integer.parseInt(((Odata) response.body()).getOdataCount());
                }
                CategoriaFragment listaAnimeFragment = CategoriaFragment.this;
                listaAnimeFragment.skip += 50;
                listaAnimeFragment.gridview.setRefreshing(false);
                ((CanalAdapter) listaAnimeFragment.gridview.getAdapter()).setItems((response.body()).getValue());
                frame.setVisibility(View.GONE);

                Log.i("Dados Carregados:",""+response.body().getValue().size());

            }

            @Override
            public void onFailure(Call<Odata> call, Throwable t) {

            }
        });
    }

    public void addItems() {

        if(shouldLoad()) {
            gridview.setRefreshing(true);
            doBackground();
        }
    }

    public boolean shouldLoad() {

        return !this.loading && this.adapter.getItemCount() < this.total;
    }



    public void loadNextPage() {
        this.loading = true;
        this.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gridview.setRefreshing(false);
                loading = false;
                addItems();
            }
        }, DELAY);
    }
}
