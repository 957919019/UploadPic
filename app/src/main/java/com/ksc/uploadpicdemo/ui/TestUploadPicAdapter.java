package com.ksc.uploadpicdemo.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ksc.uploadpicdemo.utils.GlideUtils;
import com.ksc.uploadpicdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ksc.uploadpicdemo.ui.TestUploadActivity.mImageList;

/**
 * @author kowal
 * @date 2019/6/19
 * @time 下午 12:29
 * @description
 */
public class TestUploadPicAdapter extends RecyclerView.Adapter<TestUploadPicAdapter.ViewHolder>
{

    public static final int MAX_NUMBER = 6;
    private List<Object> mPaths;
    private Activity mContext;
    private OnPickerListener mOnPickerListener;

    public TestUploadPicAdapter(Activity context)
    {
        this.mContext = context;
        mPaths = new ArrayList<>();
    }

    public void setImageUrlList(List<String> imageUrlList)
    {
        for (String path : imageUrlList)
        {
            mPaths.add(path);
        }
        this.notifyDataSetChanged();
    }

    public void setOnPickerListener(OnPickerListener onPickerListener)
    {
        mOnPickerListener = onPickerListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic_add, parent, false));
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //判断位置显示的内容 // 如果当前图片的数量是0，或者大于所选择图片的数量则显示一个带选择图片的image，同时隐藏删除按钮
        if (mPaths.size() == 0 || mPaths.size() == position)
        {
            holder.iv_delete.setVisibility(View.GONE);
            holder.iv_add.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_add_pic));
        } else
        {
            //否则每个图片直接显示删除按钮，并加载显示图片
            holder.iv_delete.setVisibility(View.VISIBLE);
            GlideUtils.getInstance().LoadFromURL(mContext, mPaths.get(position).toString(), holder.iv_add);
            // 开始调上传接口
            holder.pb_bar.setVisibility(View.VISIBLE);
            holder.pb_bar.setIndeterminate(false);
            holder.pb_bar.setMax(100);
            holder.pb_bar.setProgress(50, true);
        }
    }

    @Override
    public int getItemCount()
    {
        return mPaths.size() < MAX_NUMBER ? mPaths.size() + 1 : mPaths.size();
    }

    public interface OnPickerListener
    {
        void onPicker(int position, ImageView iv, View view);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv_add)
        ImageView iv_add;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.pb_bar)
        ProgressBar pb_bar;

        ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.iv_add, R.id.iv_delete})
        public void onViewClicked(View view)
        {
            switch (view.getId())
            {
                case R.id.iv_add:
                    mOnPickerListener.onPicker(getLayoutPosition(), iv_add, view);
                    break;
                case R.id.iv_delete:
                    mPaths.remove(getLayoutPosition());
                    mImageList.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    break;
            }
        }
    }
}
