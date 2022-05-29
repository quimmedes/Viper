package com.kimede.viper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kimede.viper.Models.Canal;
import com.kimede.viper.Models.Configure;
import com.kimede.viper.fragments.AniFragment;
import com.kimede.viper.fragments.BaseFragment;
import com.kimede.viper.utils.SimpleDatabaseHelper;

import java.util.List;

public class ActivityEpisodio extends Activity implements OnClickListener {
    boolean abertura;
    public AdView adView;
    public Canal canal;
    public ImageButton back;
    public SimpleDatabaseHelper db;
    public CheckBox favoritar;
    public BaseFragment frag;
    public InterstitialAd interstitial;
    public ViewGroup layout;
    public String tagFrag;
    public TextView titulo;
    public ImageView searchbtn;
    public String Banner;
    public String Instertial;
    public boolean carregado = false;

    class C08231 extends AdListener {
        C08231() {
        }

        public void onAdLoaded() {
            super.onAdLoaded();
            ActivityEpisodio.this.layout.setVisibility(View.VISIBLE);
            ActivityEpisodio.this.layout.removeAllViews();
            ActivityEpisodio.this.layout.addView(ActivityEpisodio.this.adView);
        }
    }

    public void removeAllViews(){
        layout.removeAllViews();
    }

    class C08242 extends AdListener {
        C08242() {
        }

        public void onAdClosed() {
            super.onAdClosed();
            loadInterstitial();
        }
    }

    class C08253 implements OnClickListener {
        C08253() {
        }

        public void onClick(View view) {
            if (ActivityEpisodio.this.db.isFavorito(ActivityEpisodio.this.canal.getId().longValue()).booleanValue()) {
                ActivityEpisodio.this.db.delete(ActivityEpisodio.this.canal.getId().longValue());
                ActivityEpisodio.this.favoritar.setChecked(false);
                ActivityEpisodio.this.favoritar.refreshDrawableState();
                Toast.makeText(ActivityEpisodio.this, ActivityEpisodio.this.canal.getNome() + " - Foi removido dos Favoritos.", Toast.LENGTH_SHORT).show();
            } else if (ActivityEpisodio.this.canal.getId().longValue() == ActivityEpisodio.this.db.add(ActivityEpisodio.this.canal.getId().longValue(), ActivityEpisodio.this.canal.getNome(), ActivityEpisodio.this.canal.getImagem())) {
                Toast.makeText(ActivityEpisodio.this, ActivityEpisodio.this.canal.getNome() + " - Foi adicionado aos Favoritos.", Toast.LENGTH_SHORT).show();
                ActivityEpisodio.this.favoritar.setChecked(true);
                ActivityEpisodio.this.favoritar.refreshDrawableState();
            }
        }
    }

    public ActivityEpisodio() {
        this.tagFrag = "Episodio";
        this.db = new SimpleDatabaseHelper(this);
        this.abertura = false;
    }

    public void ShowFull() {
        displayInterstitial();
        this.abertura = true;
    }

     public void displayInterstitial() {

        if (this.interstitial != null && this.interstitial.isLoaded()) {
            this.interstitial.show();
        }
        else
        {
            loadInterstitial();
            this.abertura = false;
        }

    }

    public void onBackPressed() {
        finish();
    }

    public void onClick(View view) {
        if (view.getId() == com.kimede.viper.R.id.btnImg_Back) {
            onBackPressed();
        }
    }

    public void setTitulo(String titulo){

        this.titulo.setText(titulo);

    }

    public boolean verify(){

        if(!carregado){

            List<Configure> configure =  ((GoogleAnalyticsApp)getApplication()).configure;

            if(configure != null && configure.size()>0)
            {
                carregado = true;
                Banner = configure.get(0).getBanner();
                Instertial = configure.get(0).getInsterstial();
                Log.i("Banner:",Banner);
                Log.i("Interstial:",Instertial);
            }
            else
            {
                carregado = false;
                ((GoogleAnalyticsApp)getApplication()).getIdBanners();

            }

        }
        return  carregado;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_second);

        this.titulo = (TextView) findViewById(R.id.tv_title);
        this.searchbtn = (ImageView) findViewById(R.id.pesq);
        setTitulo("Anime");
        this.layout = (ViewGroup) findViewById(R.id.banner);


        if(verify()) {
            this.adView = new AdView(this);
            this.adView.setAdSize(AdSize.SMART_BANNER);
            this.adView.setAdUnitId(Banner);
            Builder builder = new Builder();
            this.adView.setAdListener(new C08231());
            this.adView.loadAd(builder.build());
        }

         loadInterstitial();

        this.abertura = false;
        this.back = (ImageButton) findViewById(R.id.btnImg_Back);
        this.back.setOnClickListener(this);
        if (bundle == null) {
            this.frag = new AniFragment();
            Bundle bundle2 = new Bundle();
            this.canal = (Canal) getIntent().getSerializableExtra("anime");
            bundle2.putString("anime", this.canal.getId().toString());
            this.frag.setArguments(bundle2);
            this.tagFrag = "Ani";
            getFragmentManager().beginTransaction().replace(com.kimede.viper.R.id.conteudo, this.frag, this.tagFrag).commit();
        } else {
            this.tagFrag = bundle.getString("tag");
            this.canal = (Canal) bundle.getSerializable("anime");
            this.frag = (BaseFragment) getFragmentManager().findFragmentByTag(this.tagFrag);
        }
        this.favoritar = (CheckBox) findViewById(com.kimede.viper.R.id.favoritar);
        this.favoritar.setChecked(false);
        this.favoritar.refreshDrawableState();
        if (this.db.isFavorito(this.canal.getId())) {
            this.favoritar.setChecked(true);
        }
        this.favoritar.refreshDrawableState();
        this.favoritar.setOnClickListener(new C08253());
    }

    public void loadInterstitial(){

        if(verify()) {

            this.interstitial = new InterstitialAd(this);
            this.interstitial.setAdUnitId(Instertial);
            this.interstitial.setAdListener(new C08242());
            this.interstitial.loadAd(new Builder().build());

        }

    }

    public void onPause() {
        if (this.adView != null) {
            this.adView.pause();
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        if (this.adView != null) {
            this.adView.resume();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putSerializable("anime", this.canal);
        bundle.putSerializable("tag", this.tagFrag);
        super.onSaveInstanceState(bundle);
    }

    public void setTagFrag(String str) {
        this.tagFrag = str;
    }

    public void switchContent(BaseFragment fragment, String str) {
        this.frag = fragment;
        getFragmentManager().beginTransaction().replace(com.kimede.viper.R.id.conteudo, this.frag, str).commit();
    }
}
