package com.kimede.viper.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.kimede.viper.Models.MenuItem;
import java.util.List;

public class MenuAdapter extends ArrayAdapter<MenuItem> {
    private static LayoutInflater _inflater;
    private final Activity _context;
    private List<MenuItem> _list;

    static {
        _inflater = null;
    }

    public MenuAdapter(Activity activity, List<MenuItem> list) {
        super(activity, com.kimede.viper.R.layout.row_menu, list);
        this._context = activity;
        this._list = list;
        _inflater = this._context.getLayoutInflater();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = _inflater.inflate(com.kimede.viper.R.layout.row_menu, null);
        }
        MenuItem menuItem = this._list.get(i);
        view.setId(Integer.valueOf(menuItem.get_id()).intValue());
        TextView textView = (TextView) view.findViewById(com.kimede.viper.R.id.info_categoria);
        ImageView imageView = (ImageView) view.findViewById(com.kimede.viper.R.id.menu_image);
        textView.setText(menuItem.get_titleRes());
        imageView.setImageResource(menuItem.get_imageRes());
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        layoutParams.setMargins(20, 10, 0, 10);
        textView.setLayoutParams(layoutParams);
        return view;
    }
}
