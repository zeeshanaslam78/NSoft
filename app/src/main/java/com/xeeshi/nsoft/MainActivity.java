package com.xeeshi.nsoft;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xeeshi.nsoft.Objects.Data;
import com.xeeshi.nsoft.Objects.User;
import com.xeeshi.nsoft.Utils.Common;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyProfileFragment.UpdateProfileInfo {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private RoundedImageView nhm_img_profile;
    private TextView nhm_txt_name;

    private ImageLoader imageLoader;
    private NavigationView navigationView;

    public void changeNavigationSelection(int id) {
        if (null != navigationView) {
            switch (id) {
                case R.id.nav_staff_list:
                    navigationView.setCheckedItem(R.id.nav_staff_list);
                    break;
                case R.id.nav_my_profile:
                    navigationView.setCheckedItem(R.id.nav_my_profile);
                    break;
                case R.id.nav_favorites:
                    navigationView.setCheckedItem(R.id.nav_favorites);
                    break;
                case R.id.nav_settings:
                    navigationView.setCheckedItem(R.id.nav_settings);
                    break;
            }
        }
    }

    public void hideNavigation(int id, boolean visible) {
        if (null != navigationView) {
            navigationView.getMenu().getItem(id).setVisible(visible);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageLoader = ImageLoader.getInstance();

        getFragmentManager().
                beginTransaction().
                add(R.id.main_frame, StaffListFragment.newInstance()).
                addToBackStack(StaffListFragment.TAG).
                commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_staff_list);
        toolbar.setTitle(getResources().getString(R.string.staff_list));
        navigationView.setNavigationItemSelectedListener(this);


        List<User> userList = User.getAll();
        if (userList.size()>0) {
            hideNavigation(2, true);
        } else {
            hideNavigation(2, false);
        }


        // getting refference of header views
        View header = navigationView.getHeaderView(0);
        nhm_img_profile = (RoundedImageView) header.findViewById(R.id.nhm_img_profile);
        nhm_txt_name = (TextView) header.findViewById(R.id.nhm_txt_name);

        GetDataLocallyAndUpdateUI();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() == 1) {
            this.finish();
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_staff_list) {
            if (null!=toolbar)
                toolbar.setTitle(getResources().getString(R.string.staff_list));

            if (AddAndReplaceNewFragment(StaffListFragment.TAG)) {
                getFragmentManager().
                        beginTransaction().
                        setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_out_right, R.animator.slide_in_right).
                        replace(R.id.main_frame, StaffListFragment.newInstance()).
                        addToBackStack(StaffListFragment.TAG).
                        commit();
            }

        } else if (id == R.id.nav_my_profile) {
            if (null!=toolbar)
                toolbar.setTitle(getResources().getString(R.string.my_profile));

            if (AddAndReplaceNewFragment(MyProfileFragment.TAG)) {
                getFragmentManager().
                        beginTransaction().
                        setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_out_right, R.animator.slide_in_right).
                        replace(R.id.main_frame, MyProfileFragment.newInstance()).
                        addToBackStack(MyProfileFragment.TAG).
                        commit();
            }

        } else if (id == R.id.nav_favorites) {
            if (null!=toolbar)
                toolbar.setTitle(getResources().getString(R.string.favorites));

            if (AddAndReplaceNewFragment(FavoriteListFragment.TAG)) {
                getFragmentManager().
                        beginTransaction().
                        setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_out_right, R.animator.slide_in_right).
                        replace(R.id.main_frame, FavoriteListFragment.newInstance()).
                        addToBackStack(FavoriteListFragment.TAG).
                        commit();
            }

        } else if (id == R.id.nav_settings) {
            if (null!=toolbar)
                toolbar.setTitle(getResources().getString(R.string.settings));

            if (AddAndReplaceNewFragment(SettingsFragment.TAG)) {
                getFragmentManager().
                        beginTransaction().
                        setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_out_right, R.animator.slide_in_right).
                        replace(R.id.main_frame, SettingsFragment.newInstance()).
                        addToBackStack(SettingsFragment.TAG).
                        commit();
            }

        } else if (id == R.id.nav_clear_cache) {
            Common.trimCache(MainActivity.this);
            Common.trimSharedPrefs(this);
            Common.trimCodeCache(this);
            Common.trimFilesFolder(this);
            Common.trimLibFolder(this);
            this.deleteDatabase("NSoft.db");


            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            // Kill app process
            System.exit(0);
            //Runtime.getRuntime().exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logFragmentnName(String state) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "onNavigationItemSelected: " + state +  " Size  " + getFragmentManager().getBackStackEntryCount());
            Log.e(TAG, "onNavigationItemSelected: " + state +  " Id  " + getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount()-1).getId());
            Log.e(TAG, "onNavigationItemSelected: " + state +  " Name  " + getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount()-1).getName());
        }
    }


    private boolean AddAndReplaceNewFragment(String fragmentName) {
        if (getFragmentManager().getBackStackEntryCount()>0) {
            String fragmentOnTop = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount()-1).getName();
            if (fragmentOnTop.equals(fragmentName)) {
                return false;
            }
        }
        return true;
    }


    private void GetDataLocallyAndUpdateUI() {
        Data personalData = Data.getSingleItem();
        UpdateNavHeaderUI(personalData);
    }

    private void UpdateNavHeaderUI(Data personalData) {
        if (null!=personalData) {

            if (null!= personalData.getName() && personalData.getName().length()>0) {
                if (null!=nhm_txt_name)
                    nhm_txt_name.setText(personalData.getName());
            }
            if (null!=nhm_img_profile) {
                if (null == personalData.getPhoto()) {
                    nhm_img_profile.setImageResource(android.R.color.white);
                } else {
                    ImageSize imageSize = new ImageSize(nhm_img_profile.getWidth(), nhm_img_profile.getHeight());
                    imageLoader.loadImage(personalData.getPhoto(), imageSize, new SimpleImageLoadingListener() {

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            nhm_img_profile.setImageBitmap(loadedImage);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void updateProfileData(Data data) {
        UpdateNavHeaderUI(data);
    }
}
