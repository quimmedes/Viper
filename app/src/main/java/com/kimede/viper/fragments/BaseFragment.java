package com.kimede.viper.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

public class BaseFragment extends Fragment {

    private static final int PERMISSION_REQUEST_BLOCK_INTERNAL = 555;
    private static final String PERMISSION_SHARED_PREFERENCES = "permissions";
    private Runnable permissionBlock;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_BLOCK_INTERNAL) {
            boolean allPermissionsGranted = true;

            for (int iGranting : grantResults) {
                if (iGranting != PermissionChecker.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted && permissionBlock != null) {
                permissionBlock.run();
            }

            permissionBlock = null;
        }
    }

    public void runNowOrAskForPermissionsFirst(String permission, Runnable block) {
        if (hasPermission(permission)) {
            block.run();
        } else if (!hasPermissionOrWillAsk(permission)) {
            permissionBlock = block;
            askForPermission(permission, PERMISSION_REQUEST_BLOCK_INTERNAL);
        }
    }

    public boolean hasPermissionOrWillAsk(String permission) {
        boolean hasPermission = hasPermission(permission);
        boolean hasAsked = hasPreviouslyAskedForPermission(permission);
        boolean shouldExplain = shouldShowRequestPermissionRationale(permission);

        return hasPermission || (hasAsked && !shouldExplain);
    }

    private boolean hasPermission(String permission) {
        if(!canMakeSmores())
        return true;

        return (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED);
    }


    private boolean canMakeSmores(){

        return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    private boolean hasPreviouslyAskedForPermission(String permission) {
        SharedPreferences prefs = getContext().getSharedPreferences(PERMISSION_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getBoolean(permission, false);
    }

    private void askForPermission(String permission, int requestCode) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(PERMISSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();

        editor.putBoolean(permission, true);
        editor.apply();

        requestPermissions(new String[] { permission }, requestCode);
    }
}