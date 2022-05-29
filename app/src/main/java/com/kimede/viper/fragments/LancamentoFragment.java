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
import com.kimede.viper.adapters.LancamentoAdapter;
import com.kimede.viper.adapters.RecyclerItemClickListener;
import com.kimede.viper.adapters.RecyclerItemClickListener.OnItemClickListener;
import com.kimede.viper.interfaces.CanalService;
import com.kimede.viper.utils.AutofitRecyclerView;
import com.kimede.viper.utils.MarginDecoration;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class LancamentoFragment extends Fragment {
    LancamentoAdapter adapter;
    List<Canal> canals;
    FrameLayout frame;
    AutofitRecyclerView gridview;

    /* renamed from: com.kimede.giganimaonline.fragments.LancamentoFragment.1 */
    class C08431 implements OnItemClickListener {
        C08431() {
        }

        public void onItemClick(View view, int i) {
            Serializable item = ((LancamentoAdapter) LancamentoFragment.this.gridview.getAdapter()).getItem(i);
            Intent intent = new Intent(LancamentoFragment.this.getActivity(), ActivityEpisodio.class);
            intent.putExtra("anime", item);
            LancamentoFragment.this.startActivity(intent);
        }
    }

    /* renamed from: com.kimede.giganimaonline.fragments.LancamentoFragment.2 */
    class C08442 implements Callback<List<Canal>> {
        C08442() {
        }

        public void onFailure(Call<List<Canal>> call, Throwable th) {
        }

        public void onResponse(Call<List<Canal>> call, Response<List<Canal>> response) {
            LancamentoFragment.this.canals = (List) response.body();
            LancamentoFragment.this.adapter = new LancamentoAdapter(LancamentoFragment.this.getActivity(), LancamentoFragment.this.canals);
            LancamentoFragment.this.gridview.addItemDecoration(new MarginDecoration(LancamentoFragment.this.getActivity()));
            LancamentoFragment.this.gridview.setHasFixedSize(false);
            LancamentoFragment.this.gridview.setAdapter(LancamentoFragment.this.adapter);
            LancamentoFragment.this.frame.setVisibility(View.GONE);
        }
    }

    public LancamentoFragment() {
        this.canals = new ArrayList();
    }

    public void doBackground() {
        new Builder().baseUrl(getString(com.kimede.viper.R.string.baseurl)).addConverterFactory(GsonConverterFactory.create()).build().create(CanalService.class).GetLancamento().enqueue(new C08442());
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (viewGroup == null) {
            return null;
        }
        View inflate = layoutInflater.inflate(com.kimede.viper.R.layout.grid_anime, null);
        this.gridview = (AutofitRecyclerView) inflate.findViewById(com.kimede.viper.R.id.gridview);
        this.gridview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new C08431()));
        this.frame = (FrameLayout) inflate.findViewById(com.kimede.viper.R.id.frame_loading);
        doBackground();
        return inflate;
    }
}
