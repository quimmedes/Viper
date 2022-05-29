package com.kimede.viper.Models;

import android.app.Fragment;
import android.os.Bundle;

public class MenuItem {
    private static int counter;
    private Bundle _args;
    private Class _class;
    private Fragment _fragment;
    private int _id;
    private int _imageRes;
    private int _titleRes;

    static {
        counter = 0;
    }

    public MenuItem(int i, int i2, Class cls, Bundle bundle) {
        int i3 = counter;
        counter = i3 + 1;
        this._id = i3;
        this._titleRes = i;
        this._imageRes = i2;
        this._class = cls;
        this._args = bundle;
    }

    public Bundle get_args() {
        return this._args;
    }

    public Class get_class() {
        return this._class;
    }

    public Fragment get_fragment() {
        return this._fragment;
    }

    public int get_id() {
        return this._id;
    }

    public int get_imageRes() {
        return this._imageRes;
    }

    public int get_titleRes() {
        return this._titleRes;
    }

    public void set_args(Bundle bundle) {
        this._args = bundle;
    }

    public void set_class(Class cls) {
        this._class = cls;
    }

    public void set_fragment(Fragment fragment) {
        this._fragment = fragment;
    }

    public void set_imageRes(int i) {
        this._imageRes = i;
    }

    public void set_titleRes(int i) {
        this._titleRes = i;
    }
}
