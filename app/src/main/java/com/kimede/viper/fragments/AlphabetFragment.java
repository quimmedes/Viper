package com.kimede.viper.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimede.viper.MyActivity;
import com.kimede.viper.R;
import com.kimede.viper.adapters.AlphabetAdapter;
import com.kimede.viper.adapters.RecyclerItemClickListener;
import com.kimede.viper.adapters.RecyclerItemClickListener.OnItemClickListener;
import com.kimede.viper.utils.AutofitRecyclerView;
import com.kimede.viper.utils.MarginDecoration;

import java.util.ArrayList;

public class AlphabetFragment extends Fragment implements OnItemClickListener{
    ArrayList<String> alphabet;
    AutofitRecyclerView gridview;


        public void onItemClick(View view, int i) {
            Bundle bundle = new Bundle();
            bundle.putString("letra", alphabet.get(i));
            Fragment listaAnimeFragment = new ListaAnimeFragment();
            listaAnimeFragment.setArguments(bundle);
            ((MyActivity) getActivity()).switchContent(listaAnimeFragment);
        }


    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (viewGroup == null) {
            return null;
        }
        View inflate = layoutInflater.inflate(R.layout.alphabet_gridview, null);
        gridview = (AutofitRecyclerView) inflate.findViewById(R.id.alpha_grid);
        alphabet = new ArrayList();
        alphabet.add("#");
        alphabet.add("A");
        alphabet.add("B");
        alphabet.add("C");
        alphabet.add("D");
        alphabet.add("E");
        alphabet.add("F");
        alphabet.add("G");
        alphabet.add("H");
        alphabet.add("I");
        alphabet.add("J");
        alphabet.add("K");
        alphabet.add("L");
        alphabet.add("M");
        alphabet.add("N");
        alphabet.add("O");
        alphabet.add("P");
        alphabet.add("Q");
        alphabet.add("R");
        alphabet.add("S");
        alphabet.add("T");
        alphabet.add("U");
        alphabet.add("V");
        alphabet.add("W");
        alphabet.add("X");
        alphabet.add("Y");
        alphabet.add("Z");
        Adapter alphabetAdapter = new AlphabetAdapter(getActivity(), alphabet);
        gridview.addItemDecoration(new MarginDecoration(getActivity(), R.dimen.dim_10));
        gridview.setHasFixedSize(false);
        gridview.setAdapter(alphabetAdapter);
        gridview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        return inflate;
    }
}
