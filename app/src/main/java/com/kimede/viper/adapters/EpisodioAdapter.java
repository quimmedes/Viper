package com.kimede.viper.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.kimede.viper.Models.Videos;
import com.kimede.viper.utils.CircleProgressBarDrawable;
import java.util.List;

public class EpisodioAdapter extends Adapter<EpisodioAdapter.EpisodioViewHolder> {
    private final Context _context;
    private List<Videos> _list;
    private Long map;
    ImagePipeline imagePipeline;

    public class EpisodioViewHolder extends ViewHolder {
        SimpleDraweeView img;
        LinearLayout layout;
        TextView textNome;

        public EpisodioViewHolder(View view) {
            super(view);
            this.img = (SimpleDraweeView) view.findViewById(com.kimede.viper.R.id.img_shot);
            this.textNome = (TextView) view.findViewById(com.kimede.viper.R.id.txt_nome);
            this.layout = (LinearLayout) view.findViewById(com.kimede.viper.R.id.layoutepisodio);
        }
    }

    public EpisodioAdapter(Activity activity, List<Videos> list, Long l) {
        this._context = activity;
        this._list = list;
        this.map = l;
        imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();

    }

    public Videos getItem(int i) {
        return this._list.get(i);
    }

    public int getPosition(String valor){

        for (int x=0;x<_list.size();x++){
            if(_list.get(x).getNome().contains(valor))
            {
                return x;

            }
        }

        return 0;

    }

    public int getItemCount() {
        return this._list.size();
    }

    public void onBindViewHolder(EpisodioViewHolder episodioViewHolder, int i) {
        Videos videos = this._list.get(i);
        if (videos.getNome().contains("-") || videos.getNome().contains("\u2013")) {
            videos.setNome(videos.getNome().replace("- ", "\n").replace("\u2013 ", "\n"));
        }

        episodioViewHolder.textNome.setText(videos.getNome());
        Uri parse = Uri.parse("http://thumb.techrevolution.com.br/" + videos.getId() + ".jpg");
        imagePipeline.evictFromDiskCache(parse);

        CircleProgressBarDrawable circleProgressBarDrawable = new CircleProgressBarDrawable();
        circleProgressBarDrawable.setBackgroundColor(this._context.getResources().getColor(com.kimede.viper.R.color.white));
        circleProgressBarDrawable.setColor(this._context.getResources().getColor(com.kimede.viper.R.color.orange));
        episodioViewHolder.img.setHierarchy(new GenericDraweeHierarchyBuilder(this._context.getResources()).setFadeDuration(30).setProgressBarImage(circleProgressBarDrawable).build());
        episodioViewHolder.img.setImageURI(parse);


        if (this.map.equals(videos.getId())) {
            episodioViewHolder.layout.setBackgroundColor(this._context.getResources().getColor(com.kimede.viper.R.color.dark_green));
        } else {
            episodioViewHolder.layout.setBackgroundColor(this._context.getResources().getColor(com.kimede.viper.R.color.black_overlay));
        }
    }

    public EpisodioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new EpisodioViewHolder(LayoutInflater.from(this._context).inflate(com.kimede.viper.R.layout.row_shots_epi, viewGroup, false));
    }

    public void setMap(Long l) {
        this.map = l;
        notifyDataSetChanged();
    }
}
