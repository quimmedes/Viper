package com.kimede.viper.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

import com.kimede.viper.R;

public class MarginDecoration extends ItemDecoration {
    private int margin;

    public MarginDecoration(Context context) {
        this.margin = (int) context.getResources().getDimension(R.dimen.dp_2);
    }

    public MarginDecoration(Context context, int i) {
        this.margin = context.getResources().getDimensionPixelSize(i);
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        rect.set(this.margin, this.margin, this.margin, this.margin);
    }
}
