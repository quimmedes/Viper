package com.kimede.viper.fragments;

import android.graphics.Color;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by kimede on 16/06/2017.
 */

    import android.app.Fragment;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
import android.webkit.WebViewClient;

/**
     * A fragment that displays a WebView.
     * <p>
     * The WebView is automically paused or resumed when the Fragment is paused or resumed.
     */
    public class MaisFramework extends Fragment {
        private WebView mWebView;
        private boolean mIsWebViewAvailable;
        public MaisFramework() {
        }
        /**
         * Called to instantiate the view. Creates and returns the WebView.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (mWebView != null) {
                mWebView.destroy();
            }
            mWebView = new WebView(getActivity());
            mIsWebViewAvailable = true;
            mWebView.loadUrl("");

            // Enable Javascript
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            // Force links and redirects to open in the WebView instead of in a browser
            mWebView.setWebViewClient(new WebViewClient());


            return mWebView;
        }
        /**
         * Called when the fragment is visible to the user and actively running. Resumes the WebView.
         */
        @Override
        public void onPause() {
            super.onPause();
            mWebView.onPause();
        }
        /**
         * Called when the fragment is no longer resumed. Pauses the WebView.
         */
        @Override
        public void onResume() {
            mWebView.onResume();
            super.onResume();
        }
        /**
         * Called when the WebView has been detached from the fragment.
         * The WebView is no longer available after this time.
         */
        @Override
        public void onDestroyView() {
            mIsWebViewAvailable = false;
            super.onDestroyView();
        }
        /**
         * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
         */
        @Override
        public void onDestroy() {
            if (mWebView != null) {
                mWebView.destroy();
                mWebView = null;
            }
            super.onDestroy();
        }
        /**
         * Gets the WebView.
         */
        public WebView getWebView() {
            return mIsWebViewAvailable ? mWebView : null;
        }


}
