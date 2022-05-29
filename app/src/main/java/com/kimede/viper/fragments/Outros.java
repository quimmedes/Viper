package com.kimede.viper.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Outros extends Fragment {

    /* renamed from: com.kimede.giganimaonline.fragments.Outros.1 */
    class C08591 implements OnClickListener {
        C08591() {
        }

        public void onClick(View view) {
            try {
                Intent intent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", "", null));
                intent.putExtra("android.intent.extra.SUBJECT", "Giganima");
                Outros.this.startActivity(Intent.createChooser(intent, "Enviar email..."));
            } catch (InstantiationException e) {
            }
        }
    }

    /* renamed from: com.kimede.giganimaonline.fragments.Outros.2 */
    class C08602 implements OnClickListener {
        C08602() {
        }

        public void onClick(View view) {
            try {
                Outros.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=")));
            } catch (InstantiationException e) {
            }
        }
    }

    /* renamed from: com.kimede.giganimaonline.fragments.Outros.3 */
    class C08613 implements OnClickListener {
        C08613() {
        }

        public void onClick(View view) {
            try {
                Outros.trimCache(Outros.this.getActivity());
                Outros.this.getActivity().deleteDatabase("bancodados.db");
                Toast.makeText(Outros.this.getActivity(), "Limpando Chache e Database", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static void trimCache(Context context) {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                deleteDir(cacheDir);
            }
        } catch (Exception e) {
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(com.kimede.viper.R.layout.outros, null);
        inflate.findViewById(com.kimede.viper.R.id.suporte).setOnClickListener(new C08591());
        inflate.findViewById(com.kimede.viper.R.id.avaliar).setOnClickListener(new C08602());
        inflate.findViewById(com.kimede.viper.R.id.limpar).setOnClickListener(new C08613());
        return inflate;
    }
}
