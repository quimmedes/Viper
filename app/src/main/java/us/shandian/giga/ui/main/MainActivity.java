package us.shandian.giga.ui.main;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.kimede.viper.GoogleAnalyticsApp;
import com.kimede.viper.Models.Configure;
import com.kimede.viper.R;
import us.shandian.giga.get.DownloadManager;
import us.shandian.giga.service.DownloadManagerService;
import us.shandian.giga.ui.adapter.NavigationAdapter;
import us.shandian.giga.ui.common.ToolbarActivity;
import us.shandian.giga.ui.fragment.MissionsFragment;
import us.shandian.giga.ui.fragment.AllMissionsFragment;
import us.shandian.giga.ui.fragment.DownloadedMissionsFragment;
import us.shandian.giga.ui.fragment.DownloadingMissionsFragment;
import us.shandian.giga.ui.settings.SettingsActivity;
import us.shandian.giga.ui.web.BrowserActivity;
import us.shandian.giga.util.Utility;

public class MainActivity extends ToolbarActivity implements AdapterView.OnItemClickListener

{
	public static final String INTENT_DOWNLOAD = "us.shandian.giga.intent.DOWNLOAD";
	public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 99;


	private MissionsFragment mFragment;
	private DrawerLayout mDrawer;
	private ListView mList;
	private NavigationAdapter mAdapter;
	private ActionBarDrawerToggle mToggle;
	private DownloadManager mManager;
	private DownloadManagerService.DMBinder mBinder;

	private String mPendingUrl = "";
	private String nome = "";
	private SharedPreferences mPrefs;
	private int mSelection = 0;
	private AdView mAdView;
	private InterstitialAd mInterstitialAd;
	public String Banner;
	public String Instertial;
	public boolean carregado = false;



	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName p1, IBinder binder) {
			mBinder = (DownloadManagerService.DMBinder) binder;
			mManager = mBinder.getDownloadManager();
		}

		@Override
		public void onServiceDisconnected(ComponentName p1) {

		}

	};

	public boolean verify(){

		if(!carregado){

			List<Configure> configure =  ((GoogleAnalyticsApp)getApplication()).configure;

			if(configure != null && configure.size()>0)
			{
				carregado = true;
				Banner = configure.get(0).getBanner();
				Instertial = configure.get(0).getInsterstial();
				Log.i("Banner:",Banner);
				Log.i("Interstial:",Instertial);
			}
			else
			{
				carregado = false;
				((GoogleAnalyticsApp)getApplication()).getIdBanners();

			}

		}
		return  carregado;
	}

	@Override
	@TargetApi(21)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(verify()) {
			this.mAdView = new AdView(this);
			this.mAdView.setAdSize(AdSize.SMART_BANNER);
			this.mAdView.setAdUnitId(Banner);
			AdRequest.Builder builder = new AdRequest.Builder();
			this.mAdView.loadAd(builder.build());
			FrameLayout ads = Utility.findViewById(this,R.id.ad_view);
			ads.addView(this.mAdView);

		}

		//CrashHandler.init(this);
		//CrashHandler.register();

		// Service
		if(mBinder==null) {
			Intent i = new Intent();
			i.setClass(this, DownloadManagerService.class);
			bindService(i, mConnection, Context.BIND_AUTO_CREATE);
			startService(i);
		}

		getSupportActionBar().setDisplayUseLogoEnabled(false);


		// Here, thisActivity is the current activity

		mPrefs = getSharedPreferences("threads", Context.MODE_PRIVATE);

		// Drawer
		mDrawer = Utility.findViewById(this, R.id.drawer);
		mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, 0, 0);
		mToggle.setDrawerIndicatorEnabled(true);
		mDrawer.setDrawerListener(mToggle);

		if (Build.VERSION.SDK_INT >= 21) {
			findViewById(R.id.nav).setElevation(20.0f);
		} else {
			mDrawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.LEFT);
		}

		mList = Utility.findViewById(this, R.id.nav_list);
		mAdapter = new NavigationAdapter(this, R.array.drawer_items, R.array.drawer_icons);
		mList.setAdapter(mAdapter);


		// Fragment
		getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				updateFragments();
				getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
		mList.setOnItemClickListener(this);

		if(getIntent()!=null && getIntent().getStringExtra("link")!=null && getIntent().getStringExtra("nome")!=null){
			mPendingUrl=getIntent().getStringExtra("link");
			nome = getIntent().getStringExtra("nome");
			showUrlDialog();


			if(verify()) {
				// Create the InterstitialAd and set the adUnitId.
				mInterstitialAd = new InterstitialAd(this);
				// Defined in res/values/strings.xml
				mInterstitialAd.setAdUnitId(Instertial);

				if (mInterstitialAd != null && !mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
					AdRequest ads = new AdRequest.Builder().build();
					mInterstitialAd.loadAd(ads);
				}
			}


		}

}


	private void showInterstitial() {
		// Show the ad if it's ready. Otherwise toast and restart the game.
		if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		}

	}

	@Override
	protected void onDestroy() {
		if (mConnection != null) {
			unbindService(mConnection);
		}
		if (mAdView != null) {
			mAdView.destroy();
		}

		super.onDestroy();

	}

	/** Called when leaving the activity */
	@Override
	public void onPause() {
		if (mAdView != null) {
			mAdView.pause();

		}
		super.onPause();

		if (mInterstitialAd != null && !mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
			AdRequest ads = new AdRequest.Builder().build();
			mInterstitialAd.loadAd(ads);
		}
	}


	/** Called when returning to the activity */
	@Override
	public void onResume() {
		super.onResume();
		if (mAdView != null) {
			mAdView.resume();
		}
		showInterstitial();

	}


	@Override
	protected int getLayoutResource() {
		return R.layout.main;
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mDrawer.closeDrawer(Gravity.LEFT);
		if (position < 3) {
			if (position != mSelection) {
				mSelection = position;
				updateFragments();
			}
		} else if (position == 4) {
			Intent i = new Intent();
			i.setAction(Intent.ACTION_VIEW);
			i.setClass(this, BrowserActivity.class);
			startActivity(i);
		} else if (position == 5) {
			Intent i = new Intent();
			i.setAction(Intent.ACTION_VIEW);
			i.setClass(this, SettingsActivity.class);
			startActivity(i);
		}
	}

	private void updateFragments() {
		switch (mSelection) {
			case 0:
				mFragment = new AllMissionsFragment();
				break;
			case 1:
				mFragment = new DownloadingMissionsFragment();
				break;
			case 2:
				mFragment = new DownloadedMissionsFragment();
				break;
		}
		getFragmentManager().beginTransaction()
				.replace(R.id.frame, mFragment)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();

		for (int i = 0; i < 3; i++) {
			View v = mList.getChildAt(i);

			ImageView icon = Utility.findViewById(v, R.id.drawer_icon);
			TextView text = Utility.findViewById(v, R.id.drawer_text);

			if (i == mSelection) {
				v.setBackgroundResource(R.color.light_gray);
				icon.setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);
				text.setTextColor(getResources().getColor(R.color.blue));
			} else {
				v.setBackgroundResource(android.R.color.transparent);
				icon.setColorFilter(null);
				text.setTextColor(getResources().getColor(R.color.gray));
			}
		}
	}

	private void showUrlDialog() {


		// Create the view
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog_url, null);
		final EditText text = Utility.findViewById(v, R.id.url);
		text.setVisibility(View.GONE);
		final EditText name = Utility.findViewById(v, R.id.file_name);
		final TextView tCount = Utility.findViewById(v, R.id.threads_count);
		final SeekBar threads = Utility.findViewById(v, R.id.threads);
		final Toolbar toolbar = Utility.findViewById(v, R.id.toolbar);

		threads.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
				tCount.setText(String.valueOf(progress + 1));
			}

			@Override
			public void onStartTrackingTouch(SeekBar p1) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar p1) {

			}

		});

		int def = mPrefs.getInt("threads", 1);
		threads.setProgress(def - 1);
		tCount.setText(String.valueOf(def));



		if (mPendingUrl != null && nome!=null) {
			text.setText(mPendingUrl);
			name.setText(nome);
		}

		toolbar.setTitle(R.string.add);
		toolbar.setNavigationIcon(R.drawable.ic_add_white_24dp);
		toolbar.inflateMenu(R.menu.dialog_url);

		// Show the dialog
		final AlertDialog dialog = new AlertDialog.Builder(this)
				.setCancelable(true)
				.setView(v)
				.create();

		dialog.show();


		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if (item.getItemId() == R.id.okay) {
					String url = text.getText().toString().trim();
					String fName = name.getText().toString().trim();

					File f = new File(mManager.getLocation() + "/" + fName);

					if (f.exists()) {
						Toast.makeText(MainActivity.this, R.string.msg_exists, Toast.LENGTH_SHORT).show();
					} else if (!checkURL(url)) {
						Toast.makeText(MainActivity.this, R.string.msg_url_malform, Toast.LENGTH_SHORT).show();
					} else {

						while (mBinder == null)
						{
							Log.i("BINDER","NULO");
						}

						int res = mManager.startMission(url, fName, threads.getProgress() + 1);
						mBinder.onMissionAdded(mManager.getMission(res));
						mFragment.notifyChange();

						mPrefs.edit().putInt("threads", threads.getProgress() + 1).apply();
						dialog.dismiss();
					}

					return true;
				} else {
					return false;
				}
			}
		});

	}

	private boolean checkURL(String url) {
		try {
			URL u = new URL(url);
			u.openConnection();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}



}
