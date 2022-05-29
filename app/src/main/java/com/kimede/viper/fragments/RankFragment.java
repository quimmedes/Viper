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
import com.kimede.viper.adapters.CanalAdapter;
import com.kimede.viper.adapters.RecyclerItemClickListener;
import com.kimede.viper.adapters.RecyclerItemClickListener.OnItemClickListener;
import com.kimede.viper.interfaces.CanalService;
import com.kimede.viper.utils.EndlessRecyclerView;
import com.kimede.viper.utils.EndlessRecyclerView.Pager;
import com.kimede.viper.utils.MarginDecoration;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankFragment extends Fragment implements Pager {
    private static final long DELAY = 1000;
    CanalAdapter adapter;
    ArrayList<Canal> canals;
    FrameLayout frame;
    EndlessRecyclerView gridview;
    private final Handler handler;
    private boolean loading;
    public int skip;
    public int total;

    /* renamed from: com.kimede.giganimaonline.fragments.RankFragment.1 */
    class C08651 implements OnItemClickListener {
        C08651() {
        }

        public void onItemClick(View view, int i) {
            Serializable item = ((CanalAdapter) RankFragment.this.gridview.getAdapter()).getItem(i);
            Intent intent = new Intent(RankFragment.this.getActivity(), ActivityEpisodio.class);
            intent.putExtra("anime", item);
            RankFragment.this.startActivity(intent);
        }
    }

    /* renamed from: com.kimede.giganimaonline.fragments.RankFragment.2 */
    class C08662 implements Callback<Odata> {
        C08662() {
        }

        public void onFailure(Call<Odata> call, Throwable th) {
        }

        public void onResponse(Call<Odata> call, Response<Odata> response) {
            if (response.body().getOdataCount() != null) {
                RankFragment.this.total = Integer.parseInt(response.body().getOdataCount());
            }
            RankFragment.this.frame.setVisibility(View.GONE);
            RankFragment rankFragment = RankFragment.this;
            rankFragment.skip += 50;
            ((CanalAdapter) RankFragment.this.gridview.getAdapter()).setItems(response.body().getValue());
        }
    }

    /* renamed from: com.kimede.giganimaonline.fragments.RankFragment.3 */
    class C08673 implements Runnable {
        C08673() {
        }

        public void run() {
            RankFragment.this.gridview.setRefreshing(false);
            RankFragment.this.loading = false;
            RankFragment.this.addItems();
        }
    }

    public RankFragment() {
        this.total = 0;
        this.loading = false;
        this.handler = new Handler();
        this.skip = 0;
    }

    public void addItems() {
        doBackground();
    }

    public void doBackground() {
        new Builder().baseUrl(getString(com.kimede.viper.R.string.baseurl)).addConverterFactory(GsonConverterFactory.create()).build().create(CanalService.class).GetOdataAlphabet(null, "Id,Nome,Imagem,Rank", "Rank desc", String.valueOf(this.skip), "allpages").enqueue(new C08662());
    }

    public void loadNextPage() {
        this.loading = true;

        this.handler.postDelayed(new C08673(), DELAY);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (viewGroup == null) {
            return null;
        }
        View inflate = layoutInflater.inflate(com.kimede.viper.R.layout.grid_anime, null);
        this.skip = 0;
        this.total = 0;
        this.canals = new ArrayList();
        this.gridview = (EndlessRecyclerView) inflate.findViewById(com.kimede.viper.R.id.gridview);
        this.adapter = new CanalAdapter(getActivity(), this.canals);
        this.gridview.addItemDecoration(new MarginDecoration(getActivity()));
        this.gridview.setHasFixedSize(false);
        this.gridview.setProgressView(com.kimede.viper.R.layout.item_progress);
        this.gridview.setAdapter(this.adapter);
        this.gridview.setPager(this);
        this.gridview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new C08651()));
        this.frame = (FrameLayout) inflate.findViewById(com.kimede.viper.R.id.frame_loading);
        if (bundle != null) {
            this.skip = bundle.getInt("skip");
            this.total = bundle.getInt("total");
            this.adapter = new CanalAdapter(getActivity(), (ArrayList) bundle.getSerializable("animes"));
            this.gridview.setAdapter(this.adapter);
            this.frame.setVisibility(View.GONE);
            this.gridview.getLayoutManager().onRestoreInstanceState(bundle.getParcelable("position"));
            return inflate;
        }
        doBackground();
        return inflate;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("skip", this.skip);
        bundle.putInt("total", this.total);
        bundle.putSerializable("animes", ((CanalAdapter) this.gridview.getAdapter()).getList());
        bundle.putParcelable("position", this.gridview.getLayoutManager().onSaveInstanceState());
        Log.i("save: ", "" + this.adapter.getItemCount());
    }

    public boolean shouldLoad() {
        Log.i("ItemCount: ", "" + this.adapter.getItemCount());
        Log.i("Total: ", "" + this.total);
        return !this.loading && this.adapter.getItemCount() < this.total;
    }
}
