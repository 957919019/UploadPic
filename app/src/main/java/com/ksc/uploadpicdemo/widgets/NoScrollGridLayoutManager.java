package com.ksc.uploadpicdemo.widgets;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * @author kowal
 * @date 2019/4/28
 * @time 下午 07:40
 * @description
 */
public class NoScrollGridLayoutManager extends GridLayoutManager
{
    private boolean isScrollEnable = false;

    public NoScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NoScrollGridLayoutManager(Context context, int spanCount)
    {
        super(context, spanCount);
    }

    public NoScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout)
    {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollHorizontally()
    {
        return isScrollEnable;
    }
}
