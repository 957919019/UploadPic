package com.ksc.uploadpicdemo.base;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 康盛超 on 2019/2/15 at ShengBoYing Company.
 */
public abstract class BaseActivity extends RxAppCompatActivity
{
    public RxAppCompatActivity mActivity;
    Unbinder binder;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置App竖屏

        mActivity = this;
        this.setContentView(this.getLayoutId());
        binder = ButterKnife.bind(this);
        this.initView(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(layoutResID);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (binder != null) binder.unbind();
    }

    protected void setToolBar(Toolbar toolbar, String title, boolean isNeedHome)
    {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isNeedHome);
        getSupportActionBar().setDisplayShowHomeEnabled(isNeedHome);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
