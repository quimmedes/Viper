package us.shandian.giga.ui.web;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.kimede.viper.R;


import us.shandian.giga.ui.common.ToolbarActivity;
import us.shandian.giga.ui.main.MainActivity;


public class BrowserActivity extends ToolbarActivity
{
	private static final String[] VIDEO_SUFFIXES = new String[]{
		".mp4",
		".flv",
		".rm",
		".rmvb",
		".wmv",
		".avi",
		".mkv",
		".webm",
		"videoplayback",
			"r6--",
			"r1--",
			"r2--",
			"r3--",
			"r4--",
			"r5--"
	};
	
	private WebView mWeb;
	private ProgressBar mProgress;
	private EditText mUrl;
	private InputMethodManager mInput;
	public String link="";
	public String nome="";
	boolean loadingFinished = true;
	boolean redirect = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		if(getIntent()!=null && getIntent().getStringExtra("link")!=null && getIntent().getStringExtra("nome")!=null){
			 link=getIntent().getStringExtra("link");
			 nome=getIntent().getStringExtra("nome");
		}
		else finish();


		mWeb = new WebView(this);

		mWeb.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				if (!loadingFinished) {
					redirect = true;
				}

				loadingFinished = false;
				mWeb.loadUrl(request.getUrl().toString());
				getSupportActionBar().setDisplayShowCustomEnabled(false);
				getSupportActionBar().setDisplayShowTitleEnabled(true);
				return true;
			}

			@Override
			public void onPageStarted(
					WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				loadingFinished = false;
				//SHOW LOADING IF IT ISNT ALREADY VISIBLE
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				if(!redirect){
					loadingFinished = true;
				}

				if(loadingFinished && !redirect){

					showCamera(url);

				} else{
					redirect = false;
				}
			}
		});

		WebSettings webSettings = mWeb.getSettings();
		webSettings.setJavaScriptEnabled(true);

		mWeb.loadUrl(link);




	}



	void showCamera(String url) {
		Intent i = new Intent(BrowserActivity.this, MainActivity.class);
		i.putExtra("link",url);
		i.putExtra("nome",nome);
		startActivity(i);
		this.finish();

	}


	@Override
	protected int getLayoutResource() {
		return R.layout.browser;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browser, menu);
		return true;
	}



	@Override
	public void onBackPressed() {

			finish();
		}





}
