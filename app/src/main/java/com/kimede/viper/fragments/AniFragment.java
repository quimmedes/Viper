package com.kimede.viper.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kimede.viper.ActivityEpisodio;
import com.kimede.viper.Models.Canal;
import com.kimede.viper.Models.Odata;
import com.kimede.viper.R;
import com.kimede.viper.interfaces.CanalService;
import com.kimede.viper.utils.CircleProgressBarDrawable;
import com.kimede.viper.utils.TextViewEx;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class AniFragment extends BaseFragment {

   // private RatingBar ratingBar;

    /* renamed from: com.kimede.giganimaonline.fragments.AniFragment.1 */
    class C08311 implements OnClickListener {
        C08311() {
        }

        public void onClick(View view) {
            AniFragment.this.getActivity().onBackPressed();
        }
    }

    /* renamed from: com.kimede.giganimaonline.fragments.AniFragment.2 */
    class C08322 implements OnClickListener {
        C08322() {
        }

        public void onClick(View view) {
            BaseFragment listaEpisodio = new ListaEpisodio();
            Bundle bundle = new Bundle();
            bundle.putString("anime", AniFragment.this.getArguments().getString("anime"));
            listaEpisodio.setArguments(bundle);

            ((ActivityEpisodio) AniFragment.this.getActivity()).setTitulo("Episódios");
            ((ActivityEpisodio) AniFragment.this.getActivity()).switchContent(listaEpisodio, "Episodio");
        }
    }

    /* renamed from: com.kimede.giganimaonline.fragments.AniFragment.3 */
    class C08333 implements Callback<Odata> {
        final /* synthetic */ TextView val$ano;
        final /* synthetic */ TextView val$cat;
        final /* synthetic */ TextViewEx val$desc;
        final /* synthetic */ SimpleDraweeView val$img;
        final /* synthetic */ TextView val$nome;

        C08333(TextView textView, TextView textView2, TextView textView3, TextViewEx textViewEx, SimpleDraweeView simpleDraweeView) {
            this.val$nome = textView;
            this.val$cat = textView2;
            this.val$ano = textView3;
            this.val$desc = textViewEx;
            this.val$img = simpleDraweeView;
        }

        public void onFailure(Call<Odata> call, Throwable th) {
        }

        public void onResponse(Call<Odata> call, Response<Odata> response) {
            Canal canal = response.body().getValue().get(0);
            this.val$cat.setText(canal.getCategoria());
            this.val$ano.setText(canal.getAno());
            this.val$desc.setText(canal.getDesc(),true);

            this.val$nome.setText(canal.getNome());

            Uri parse = Uri.parse(canal.getImagem());
            if (AniFragment.this.getActivity() != null) {
                CircleProgressBarDrawable circleProgressBarDrawable = new CircleProgressBarDrawable();
                circleProgressBarDrawable.setBackgroundColor(AniFragment.this.getActivity().getResources().getColor(com.kimede.viper.R.color.white));
                circleProgressBarDrawable.setColor(AniFragment.this.getActivity().getResources().getColor(com.kimede.viper.R.color.orange));
                this.val$img.setHierarchy(new GenericDraweeHierarchyBuilder(AniFragment.this.getActivity().getResources()).setFadeDuration(30).setProgressBarImage(circleProgressBarDrawable).build());
                this.val$img.setImageURI(parse);
                ((ActivityEpisodio) AniFragment.this.getActivity()).canal = canal;

                DecimalFormat df = new DecimalFormat(
                        "#,##0",
                        new DecimalFormatSymbols(new Locale("pt", "BR")));

                BigDecimal value = new BigDecimal(canal.getRank());

                ((ActivityEpisodio)AniFragment.this.getActivity()).setTitulo("Anime Ki: +"+df.format(value.floatValue()));
            }
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (viewGroup == null) {
            return null;
        }
        View inflate = layoutInflater.inflate(R.layout.layout_detail, null);
        TextView view = (TextView) inflate.findViewById(R.id.text_nome);
        TextView textView2 = (TextView) inflate.findViewById(R.id.text_cat);
        TextViewEx textViewEx = (TextViewEx) inflate.findViewById(R.id.text_desc);
        TextView textView3 = (TextView) inflate.findViewById(R.id.text_ano);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) inflate.findViewById(R.id.image);




        view.setText("");
        textView2.setText("");
        textViewEx.setText("");
        textView3.setText("");
        inflate.findViewById(R.id.btn_back).setOnClickListener(new C08311());
        inflate.findViewById(R.id.btn_epi).setOnClickListener(new C08322());

        /*
        ratingBar = (RatingBar) inflate.findViewById(R.id.ratingBar2);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                Toast.makeText(getActivity(),""+rating, Toast.LENGTH_SHORT).show();

            }
        });
        */


       // Utils.setFontAllView((ViewGroup) inflate);

        new Builder().baseUrl(getResources().getString(R.string.baseurl))
                .addConverterFactory(GsonConverterFactory.create()).build().create(CanalService.class).GetOdataAnime("Id eq " + getArguments().getString("anime"))
                .enqueue(new C08333(view, textView2, textView3, textViewEx, simpleDraweeView));

        return inflate;
    }


}
