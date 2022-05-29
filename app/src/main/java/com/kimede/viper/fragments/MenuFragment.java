package com.kimede.viper.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.kimede.viper.Models.MenuItem;
import com.kimede.viper.MyActivity;
import com.kimede.viper.R;
import com.kimede.viper.adapters.MenuAdapter;

import java.util.ArrayList;

import us.shandian.giga.ui.main.MainActivity;

public class MenuFragment extends BaseFragment implements OnItemClickListener {
    public ImageButton btnSearch;
    public LinearLayout Download;
    public ListView listView;
    public ArrayList<MenuItem> lstMenuItems;
    public EditText search;
    public View vw_layout;

    @SuppressWarnings("rawtypes")
    private void addMenuItem(int labelID, int drawableId, Class cl, Bundle args) {
        MenuItem mnu = new MenuItem(labelID, drawableId, cl, args);
        lstMenuItems.add(mnu);
    }



    private void initialiseMenuItems(Bundle bundle) {

        this.Download  = (LinearLayout) this.vw_layout.findViewById(R.id.layoutdown);
        this.Download.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                runNowOrAskForPermissionsFirst(Manifest.permission.WRITE_EXTERNAL_STORAGE, new Runnable() {
                        @Override
                        public void run() {

                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Intent it = new Intent(getActivity(), MainActivity.class);
                                    startActivity(it);

                                }
                            });


                        }
                    });



            }
        });
        this.search = (EditText) this.vw_layout.findViewById(R.id.searchQuery);

        this.search.setOnKeyListener(new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                doSearch();
                return true;
            }
            return false;
        }
    });


        this.btnSearch = (ImageButton) this.vw_layout.findViewById(R.id.btn_search);
        this.btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doSearch();
            }
        });
        this.listView = (ListView) this.vw_layout.findViewById(R.id.lst_menu);
        this.lstMenuItems = new ArrayList();
        addMenuItem(R.string.lista_anime, R.drawable.ico_list, AlphabetFragment.class, bundle);
        addMenuItem(R.string.latest, R.drawable.ico_plus, RecentesEpisodio.class, bundle);
        addMenuItem(R.string.popular, R.drawable.ico_plus, RankFragment.class, bundle);
        addMenuItem(R.string.lancamentos, R.drawable.ico_plus, LancamentoFragment.class, bundle);
        addMenuItem(R.string.generos, R.drawable.ico_search, Categorias.class, bundle);
        addMenuItem(R.string.ano, R.drawable.ico_search, Ano.class, bundle);
        addMenuItem(R.string.favorito, R.drawable.ico_star_checked, ListaFavoritoFragment.class, bundle);
        addMenuItem(R.string.outros, R.drawable.ico_check, Outros.class, bundle);
        addMenuItem(R.string.mais, R.drawable.ico_check, MaisFramework.class, bundle);

        this.listView.setAdapter(new MenuAdapter(getActivity(), this.lstMenuItems));
        this.listView.setOnItemClickListener(this);
    }

    private void switchFragment(Fragment fragment) {
        if (getActivity() != null && (getActivity() instanceof MyActivity)) {
            ((MyActivity) getActivity()).switchContent(fragment);
        }
    }

    public void doSearch() {
        String trim = this.search.getText().toString().trim();
        Fragment pesquisaAniFragment;
        Bundle bundle;

        pesquisaAniFragment = new PesquisaAniFragment();
        bundle = new Bundle();
        bundle.putString("letra", trim);
        pesquisaAniFragment.setArguments(bundle);
        switchFragment(pesquisaAniFragment);
        getPrincipalActivity().setTagFrag("Viper" + trim);

        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.search.getWindowToken(), 0);
        if (getPrincipalActivity().getMenu().isMenuShowing()) {
            getPrincipalActivity().getMenu().showContent();
        }
        getPrincipalActivity().setTextView("Pesquisa: " + trim);

    }


    public MyActivity getPrincipalActivity() {
        return MyActivity.class.cast(getActivity());
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (viewGroup == null) {
            return null;
        }
        this.vw_layout = layoutInflater.inflate(com.kimede.viper.R.layout.fragment_menu, viewGroup, false);
        initialiseMenuItems(bundle);
       // Utils.setFontAllView((ViewGroup) this.vw_layout);
        return this.vw_layout;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        switch (i) {
            case 0:
                getPrincipalActivity().setTextView("Lista");
                getPrincipalActivity().setTagFrag("Lista");
                break;
            case 1:
                getPrincipalActivity().setTextView("Atualizados");
                getPrincipalActivity().setTagFrag("Atualizados");
                break;
            case 2:
                getPrincipalActivity().setTextView("Populares");
                getPrincipalActivity().setTagFrag("Popular");
                break;
            case 3:
                getPrincipalActivity().setTextView(getResources().getString(com.kimede.viper.R.string.lancamentos));
                getPrincipalActivity().setTagFrag("Lancamento");
                break;
            case 4:
                getPrincipalActivity().setTextView(getResources().getString(com.kimede.viper.R.string.generos));
                getPrincipalActivity().setTagFrag("Categoria");
                break;
            case 5:
                getPrincipalActivity().setTextView("Filtrar Por Ano");
                getPrincipalActivity().setTagFrag("Ano");
                break;
            case 6:
                getPrincipalActivity().setTextView("Favoritos");
                getPrincipalActivity().setTagFrag("Favorito");
                break;
            case 7:
                getPrincipalActivity().setTextView("Outros");
                getPrincipalActivity().setTagFrag("Outros");
                break;
        }
        if (adapterView != null && (adapterView.getAdapter() instanceof MenuAdapter)) {
            MenuItem menuItem = ((MenuAdapter) adapterView.getAdapter()).getItem(i);
            Fragment fragment = menuItem.get_fragment();
            if (fragment == null) {
                fragment = Fragment.instantiate(getActivity(), menuItem.get_class().getName(), menuItem.get_args());
                menuItem.set_fragment(fragment);
            }
            switchFragment(fragment);
        }
    }



}
