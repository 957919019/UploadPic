package com.ksc.uploadpicdemo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Glide图片加载工具类
 */
public class GlideUtils
{
    private static GlideUtils instance;

    public static GlideUtils getInstance()
    {
        if (instance == null)
        {
            synchronized (GlideUtils.class)
            {
                if (instance == null)
                {
                    instance = new GlideUtils();
                }
            }
        }
        return instance;
    }


    public void LoadFromURL(Context mContext, String url, ImageView imageView)
    {
        RequestOptions options = new RequestOptions()
                .override(Target.SIZE_ORIGINAL)                            //指定图片的尺寸
                .fitCenter();
        Glide.with(mContext)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}