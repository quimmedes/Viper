package com.kimede.viper.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kimede.viper.ActivityEpisodio;
import com.kimede.viper.Models.Link;
import com.kimede.viper.Models.Videos;
import com.kimede.viper.R;
import com.kimede.viper.adapters.EpisodioAdapter;
import com.kimede.viper.adapters.MyAdapter;
import com.kimede.viper.adapters.RecyclerItemClickListener;
import com.kimede.viper.adapters.RecyclerItemClickListener.OnItemClickListener;
import com.kimede.viper.interfaces.CanalService;
import com.kimede.viper.utils.CanalPontuarMaisUm;
import com.kimede.viper.utils.MarginDecoration;
import com.kimede.viper.utils.PlayVideoActivity;
import com.kimede.viper.utils.SimpleDatabaseHelper;


import org.jsoup.Jsoup;

import java.text.Normalizer;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import us.shandian.giga.ui.main.MainActivity;

public class ListaEpisodio extends BaseFragment  {
    public EpisodioAdapter adapter;
    public Videos epi = new Videos();
    public Link link = new Link();
    public FrameLayout frame;
    public RecyclerView gridview;
    public ArrayList<Videos> list;
    public Long map;
    public  ProgressDialog dialog;

    @Nullable
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (viewGroup == null) {
            return null;
        }

        final View inflate = layoutInflater.inflate(R.layout.layout_recyclerview, null);
        this.gridview = (RecyclerView) inflate.findViewById(R.id.recycleview);
        this.gridview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                epi = ((EpisodioAdapter) gridview.getAdapter()).getItem(position);

                 dialog = ProgressDialog.show(getActivity(), "",
                        "Carregando. Por Favor Aguarde...", true, true);

                dialog.show();

                showAds();

                new Retrofit.Builder().baseUrl(getString(com.kimede.viper.R.string.baseurl)).
                        addConverterFactory(GsonConverterFactory.create()).build().
                        create(CanalService.class).GetLinksEpi(epi.getId().toString()).enqueue(new Callback<ArrayList<Link>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Link>> call, Response<ArrayList<Link>> response) {

                  //    LayoutInflater inflater = (LayoutInflater)   getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //   View view = inflater.inflate(R.layout.test, null);


                      //  final Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
                        final Spinner spinner = new Spinner(getActivity());

                        final MyAdapter spinnerAdapter = new MyAdapter(getActivity(), response.body());
                        spinner.setAdapter(spinnerAdapter);


                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                Log.i("Foi Ativado: ",""+"sim");
                                Integer indexValue = spinner.getSelectedItemPosition();
                                link = (Link) spinnerAdapter.getItem(indexValue);


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                                Log.i("Não foi ativado: ", ""+"não");
                            }
                        });

                        Builder v = new Builder(getActivity()).setTitle("Selecione uma opção")
                                .setNegativeButton("Play", new Player())
                                .setNeutralButton("Download", new Download())
                                .setPositiveButton("Externo",new Externo());

                        v.setView(spinner);

                        dialog.dismiss();

                        v.show();

                        if(getArguments().getString("anime")!=null)
                        new CanalPontuarMaisUm().execute(getArguments().getString("anime"));

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Link>> call, Throwable t) {

                        dialog.dismiss();

                    }
                });


                //Salva episodio recentes
                if (ListaEpisodio.this.getActivity() != null) {
                    new SimpleDatabaseHelper(ListaEpisodio.this.getActivity()).addEpi((long) Integer.parseInt(ListaEpisodio.this.getArguments().getString("anime")), epi.getId());
                }
                //Altera Cor
                ListaEpisodio.this.map = ListaEpisodio.this.epi.getId();
                ((EpisodioAdapter) ListaEpisodio.this.gridview.getAdapter()).setMap(ListaEpisodio.this.map);

            }
        }));


        this.frame = (FrameLayout) inflate.findViewById(com.kimede.viper.R.id.frame_loading);
        if (bundle != null) {
            this.list = (ArrayList) bundle.getSerializable("list");
            this.map = Long.valueOf(bundle.getLong("map"));
            this.adapter = new EpisodioAdapter(getActivity(), this.list, this.map);
            this.gridview.setLayoutManager(new LinearLayoutManager(getActivity()));
            this.gridview.addItemDecoration(new MarginDecoration(getActivity()));
            this.gridview.setAdapter(this.adapter);
            this.frame.setVisibility(View.GONE);
            this.gridview.getLayoutManager().onRestoreInstanceState(bundle.getParcelable("position"));




            return inflate;
        }

        doBackground();

        //remove anuncio de baixo
        ((ActivityEpisodio)getActivity()).removeAllViews();

        //adiciona a searchbox
        ((ActivityEpisodio)getActivity()).searchbtn.setVisibility(View.VISIBLE);
        ((ActivityEpisodio)getActivity()).searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewDialog alert = new ViewDialog();
                 alert.showDialog(getActivity(), "Error de conexión al servidor");

            }
        });


        return inflate;
    }


    void scroll(String valor){

        try {

            int p = ((EpisodioAdapter) ListaEpisodio.this.gridview.getAdapter()).getPosition(valor);
            ListaEpisodio.this.gridview.getLayoutManager().scrollToPosition(p);

        }catch (Exception ex){}

    }



    public class ViewDialog {

        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.search_filtro);

            final EditText search = (EditText) dialog.findViewById(R.id.searchQuery);

            search.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        scroll(search.getText().toString());
                        dialog.dismiss();
//
                        return true;
                    }
                    return false;
                }
            });



            ImageButton button = (ImageButton) dialog.findViewById(R.id.btn_search);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scroll(search.getText().toString());
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }


    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("list", this.list);
        bundle.putLong("map", map);
        bundle.putParcelable("position", this.gridview.getLayoutManager().onSaveInstanceState());
    }

    public void doBackground() {


        new Retrofit.Builder().baseUrl(getString(R.string.baseurl)).
                addConverterFactory(GsonConverterFactory.create()).build().
                create(CanalService.class).GetVideos(getArguments().getString("anime")).enqueue(new Callback<ArrayList<Videos>>() {
            @Override
            public void onResponse(Call<ArrayList<Videos>> call, Response<ArrayList<Videos>> response) {
                ListaEpisodio.this.list = response.body();
                ListaEpisodio.this.map = new SimpleDatabaseHelper(ListaEpisodio.this.getActivity()).getEpi((long) Integer.parseInt(ListaEpisodio.this.getArguments().getString("anime")));
                ListaEpisodio.this.adapter = new EpisodioAdapter(ListaEpisodio.this.getActivity(), ListaEpisodio.this.list, ListaEpisodio.this.map);
                ListaEpisodio.this.gridview.setLayoutManager(new LinearLayoutManager(ListaEpisodio.this.getActivity()));
                ListaEpisodio.this.gridview.addItemDecoration(new MarginDecoration(ListaEpisodio.this.getActivity()));
                ListaEpisodio.this.gridview.setAdapter(ListaEpisodio.this.adapter);
                ListaEpisodio.this.frame.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Videos>> call, Throwable t) {
            }
        });
    }



    public class Download implements OnClickListener {

        Download() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {


            runNowOrAskForPermissionsFirst(Manifest.permission.WRITE_EXTERNAL_STORAGE, new Runnable() {
                @Override
                public void run() {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            dialog.show();

                            new AsyncTask<Void,Void,Void>(){
                                @Override
                                protected Void doInBackground(Void... params) {

                                    try {

                                        String url = Jsoup.connect(link.getEndereco()).followRedirects(true).ignoreContentType(true).execute().url().toExternalForm();

                                        System.out.println("Status" + " : " + url);


                                        Log.i("Decodificado",url);


                                        Intent intent = new Intent(getActivity(),MainActivity.class);
                                        intent.putExtra("link",url);

                                        String nome =  Normalizer
                                                .normalize(epi.getNome(), Normalizer.Form.NFD)
                                                .replaceAll("[^\\p{ASCII}]", "");

                                        nome = nome + ".mp4";

                                        intent.putExtra("nome",nome);
                                        dialog.dismiss();
                                        startActivity(intent);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    return null;
                                }
                            }.execute();


                        }
                    });


                }
            });

        }
    }

    public class Player implements OnClickListener {

        Player() {  }

        public void onClick(DialogInterface dialogInterface, int i) {

            Intent it = new Intent(getActivity(),PlayVideoActivity.class);
            it.putExtra(PlayVideoActivity.STREAM_URL,link.getEndereco());
            it.putExtra(PlayVideoActivity.VIDEO_TITLE,epi.getNome());
            startActivity(it);

        }
    }

    public class Externo implements OnClickListener {

        Externo() {}

        public void onClick(DialogInterface dialogInterface, int i) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(Uri.parse(link.getEndereco()), "video/*");
                ListaEpisodio.this.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    void showAds() {
        ((ActivityEpisodio)getActivity()).ShowFull();
    }


}
