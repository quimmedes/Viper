package com.kimede.viper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kimede.viper.fragments.AlphabetFragment;
import com.kimede.viper.fragments.Ano;
import com.kimede.viper.fragments.Categorias;
import com.kimede.viper.fragments.LancamentoFragment;
import com.kimede.viper.fragments.ListaFavoritoFragment;
import com.kimede.viper.fragments.MenuFragment;
import com.kimede.viper.fragments.Outros;
import com.kimede.viper.fragments.PesquisaAniFragment;
import com.kimede.viper.fragments.RankFragment;
import com.kimede.viper.fragments.RecentesEpisodio;


public class MyActivity extends FragmentActivity implements OnClickListener {
    boolean abertura;
    public Fragment mContent;
    private SlidingMenu menu;
    public ImageButton reload;
    public String tagFrag;
    public TextView textView;

    /* renamed from: com.kimede.giganimaonline.MyActivity.2 */
    class Recarregar implements OnClickListener {

        public void onClick(View view) {
            if (MyActivity.this.tagFrag == "Lista") {
                MyActivity.this.mContent = new AlphabetFragment();
                MyActivity.this.getFragmentManager().beginTransaction().replace(R.id.container, MyActivity.this.mContent, MyActivity.this.tagFrag).commit();
            } else if (MyActivity.this.tagFrag == "Atualizados") {
                MyActivity.this.mContent = new RecentesEpisodio();
                MyActivity.this.getFragmentManager().beginTransaction().replace(R.id.container, MyActivity.this.mContent, MyActivity.this.tagFrag).commit();
            } else if (MyActivity.this.tagFrag == "Popular") {
                MyActivity.this.mContent = new RankFragment();
                MyActivity.this.getFragmentManager().beginTransaction().replace(R.id.container, MyActivity.this.mContent, MyActivity.this.tagFrag).commit();
            } else if (MyActivity.this.tagFrag == "Lancamento") {
                MyActivity.this.mContent = new LancamentoFragment();
                MyActivity.this.getFragmentManager().beginTransaction().replace(R.id.container, MyActivity.this.mContent, MyActivity.this.tagFrag).commit();
            } else if (MyActivity.this.tagFrag == "Categoria") {
                MyActivity.this.mContent = new Categorias();
                MyActivity.this.getFragmentManager().beginTransaction().replace(R.id.container, MyActivity.this.mContent, MyActivity.this.tagFrag).commit();
            } else if (MyActivity.this.tagFrag == "Ano") {
                MyActivity.this.mContent = new Ano();
                MyActivity.this.getFragmentManager().beginTransaction().replace(R.id.container, MyActivity.this.mContent, MyActivity.this.tagFrag).commit();
            } else if (MyActivity.this.tagFrag == "Favorito") {
                MyActivity.this.mContent = new ListaFavoritoFragment();
                MyActivity.this.getFragmentManager().beginTransaction().replace(R.id.container, MyActivity.this.mContent, MyActivity.this.tagFrag).commit();
            } else if (MyActivity.this.tagFrag == "Outros") {
                MyActivity.this.mContent = new Outros();
                MyActivity.this.getFragmentManager().beginTransaction().replace(R.id.container, MyActivity.this.mContent, MyActivity.this.tagFrag).commit();
            } else if (MyActivity.this.tagFrag.contains("Giganima")) {
                MyActivity.this.mContent = new PesquisaAniFragment();
                Bundle r0 = new Bundle();
                r0.putString("letra", MyActivity.this.tagFrag.replace("Giganima", ""));
                MyActivity.this.mContent.setArguments(r0);
                MyActivity.this.getFragmentManager().beginTransaction().replace(R.id.container, MyActivity.this.mContent, MyActivity.this.tagFrag).commit();
            }
        }
    }

    public MyActivity() {
        this.abertura = false;
    }

    private void inicializaMenu() {

        // configure the SlidingMenu
        //Configurando o botao do cabecalho para ser capaz de receber cliques e abrir o menu lateral
        ImageButton btnList = (ImageButton) findViewById(R.id.btn_list);
        btnList.setOnClickListener(this);
        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
       menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(1.0f);
        menu.setFadeEnabled(true);

        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setSlidingEnabled(true);
        menu.setMenu(R.layout.menu_frame);
        getFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new MenuFragment()).commit();

    }

    public void ShowFull() {
        this.abertura = true;
    }



    public SlidingMenu getMenu() {
        return this.menu;
    }

    public void onBackPressed() {
        if (this.menu.isMenuShowing()) {
            this.menu.showContent();
            return;
        }
        ShowFull();
        super.onBackPressed();
    }

    public void onClick(View view) {
        if (view.getId() != R.id.btn_list) {
            return;
        }
        if (this.menu.isMenuShowing()) {
            this.menu.showContent();
        } else {
            this.menu.showMenu();
        }
    }



    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my);


        inicializaMenu();
        this.textView = (TextView) findViewById(com.kimede.viper.R.id.tv_title);
        this.textView.setText("Giganima");
        this.tagFrag = "Lista";
        this.reload = (ImageButton) findViewById(com.kimede.viper.R.id.Reloading);
        this.reload.setOnClickListener(new Recarregar());
        if (bundle != null) {
            this.tagFrag = bundle.getString("tag");
            this.textView.setText(bundle.getString("text"));
            this.mContent = getFragmentManager().findFragmentByTag(this.tagFrag);
            return;
        }
        this.mContent = new AlphabetFragment();
        switchContent(this.mContent);

    }

    public void onResume() {
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putSerializable("tag", this.tagFrag);
        bundle.putSerializable("text", this.textView.getText().toString());
        super.onSaveInstanceState(bundle);
    }

    public void setTagFrag(String str) {
        this.tagFrag = str;
    }

    public void setTextView(String str) {
        this.textView.setText(str);
    }

    public void switchContent(Fragment fragment) {
        this.mContent = fragment;
        getFragmentManager().beginTransaction().replace(com.kimede.viper.R.id.container, this.mContent, this.tagFrag).commit();
        this.menu.showContent();
    }


}
