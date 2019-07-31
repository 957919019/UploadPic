package com.ksc.uploadpicdemo.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.interfaces.XPopupImageLoader;

import java.io.File;

public class ImageLoader implements XPopupImageLoader
{
    @Override
    public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView)
    {
        Glide.with(imageView).load(url).apply(new RequestOptions().centerCrop().override(Target.SIZE_ORIGINAL)).into(imageView);
    }

    @Override
    public File getImageFile(@NonNull Context context, @NonNull Object uri)
    {
        try
        {
            return Glide.with(context).downloadOnly().load(uri).submit().get();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}