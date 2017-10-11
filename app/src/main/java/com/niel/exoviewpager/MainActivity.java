package com.niel.exoviewpager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.niel.exoviewpager.gallery.ItemViewerFragment;
import com.niel.exoviewpager.gallery.PreviewPagerAdapter;
import com.niel.exoviewpager.loader.MediaLoader;
import com.niel.exoviewpager.model.GalleryModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,ItemViewerFragment.onItemClickedListner  {

    private static final int REQUEST_CODE_EXTERNAL = 100;

//    protected PreviewPagerAdapter mAdapter;

    public ViewPager mViewPager;

    protected int mPreviousPos = 0;

    private List<GalleryModel> mData = new ArrayList<>();

    private View mDecorView;

    private Toolbar toolbar;

    boolean visible = true;

    private ActionBar mActionBar;

    protected PreviewPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDecorView = getWindow().getDecorView();

        mViewPager = findViewById(R.id.imageViewerPager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        }
       if(hasStoragePermission()) {
            buildDemoDataSet();
        }




    }

    private void buildDemoDataSet() {

        MediaLoader mediaLoader = new MediaLoader(this);
        mData = mediaLoader.getAllMediaFiles();

        mAdapter = new PreviewPagerAdapter(getSupportFragmentManager(), null);
        mViewPager.addOnPageChangeListener(this);

        mAdapter.addAll(mData);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mPreviousPos,false);
        updateTheToolbar(mPreviousPos);

    }


    private void updateTheToolbar(int position) {

        if(toolbar != null && position <= mData.size() ){
            GalleryModel model = mData.get(position);
            toolbar.setTitle(model.getName());
        }
    }
    private void hideSystemUI() {
        if (mActionBar != null) mActionBar.hide();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showSystemUI() {
        if (mActionBar != null) mActionBar.show();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    @Override
    public void onItemClicked() {
        if (visible) {
            hideSystemUI();
            visible = false;
        } else {
            showSystemUI();
            visible = true;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        try {
            ((ItemViewerFragment)mAdapter.getItem(mPreviousPos)).imHiddenNow();
            ((ItemViewerFragment)mAdapter.getItem(position)).imVisibleNow();

        } catch (Exception e) {
            e.printStackTrace();
        }

        mPreviousPos = position;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public boolean hasStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_EXTERNAL);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            buildDemoDataSet();
        }
    }
}
