package com.ksc.uploadpicdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ksc.uploadpicdemo.widgets.Glide4Engine;
import com.ksc.uploadpicdemo.widgets.GridSpacingItemDecoration;
import com.ksc.uploadpicdemo.widgets.ImageLoader;
import com.ksc.uploadpicdemo.widgets.NoScrollGridLayoutManager;
import com.ksc.uploadpicdemo.utils.PermissionUtils;
import com.ksc.uploadpicdemo.R;
import com.ksc.uploadpicdemo.base.BaseActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.ksc.uploadpicdemo.utils.PermissionUtils.PERMISSION_CODE;
import static com.ksc.uploadpicdemo.utils.PermissionUtils.PERMISSION_GROUP;

public class TestUploadActivity extends BaseActivity implements TestUploadContract.View, EasyPermissions.PermissionCallbacks
{
    public static final int REQUEST_CODE_UPLOAD_PIC = 3;

    public static List<Object> mImageList;
    @BindView(R.id.rv_picList)
    RecyclerView rv_picList;
    @BindView(R.id.tb_toolbar)
    Toolbar tb_toolbar;
    private TestUploadContract.Presenter mPresenter;
    private TestUploadPicAdapter mAdapter;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_test_upload;
    }

    @Override
    protected void initView(Bundle savedInstanceState)
    {
        setToolBar(tb_toolbar, "测试", true);
        mPresenter = new TestUploadContractPresenter(mActivity, this);
        mImageList = new ArrayList<>();
        permissionUtils = new PermissionUtils();
        requiresPermission();

        mAdapter = new TestUploadPicAdapter(this);
        rv_picList.setHasFixedSize(true);
        rv_picList.setLayoutManager(new NoScrollGridLayoutManager(mActivity, 3));
        rv_picList.setItemAnimator(new DefaultItemAnimator());
        rv_picList.setNestedScrollingEnabled(false);
        rv_picList.addItemDecoration(new GridSpacingItemDecoration(3, 30, true));
        rv_picList.setAdapter(mAdapter);

        mAdapter.setOnPickerListener(new TestUploadPicAdapter.OnPickerListener()
        {
            @Override
            public void onPicker(int position, ImageView iv, final View view)
            {
                if (position == mImageList.size())
                {
                    if (position != TestUploadPicAdapter.MAX_NUMBER)
                    {
                        Matisse.from(mActivity)
                                .choose(MimeType.ofImage())
                                .theme(R.style.Matisse_Dracula)
                                .countable(true)
                                .maxSelectable(TestUploadPicAdapter.MAX_NUMBER - mImageList.size())
                                .originalEnable(false)
                                .maxOriginalSize(10)
                                .imageEngine(new Glide4Engine())
                                .forResult(REQUEST_CODE_UPLOAD_PIC);
                    }
                } else
                    new XPopup.Builder(mActivity).asImageViewer(iv, position, mImageList, new OnSrcViewUpdateListener()
                    {
                        @Override
                        public void onSrcViewUpdate(@NonNull ImageViewerPopupView popupView, int position1)
                        {
                            RecyclerView rv = (RecyclerView) view.getParent().getParent();
                            popupView.updateSrcView((ImageView) rv.getChildAt(position1).findViewById(R.id.iv_add));
                        }
                    }, new ImageLoader()).isShowSaveButton(false).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPLOAD_PIC && resultCode == RESULT_OK)
        {
            List<String> tempPath = new ArrayList<>();
            for (String path : Matisse.obtainPathResult(data))
            {
                mImageList.add(path);
                tempPath.add(path);
            }
            mAdapter.setImageUrlList(tempPath);
        }
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) // 从设置页面返回
        {
            if (resultCode == RESULT_OK)
            {
                requiresPermission();
            } else
            {
                showAlertDialog();
            }
        }
    }

    @Override
    public void showSnackBar(String s)
    {
        Snackbar.make(tb_toolbar, s, Snackbar.LENGTH_LONG).show();
    }

    private PermissionUtils permissionUtils;


    @AfterPermissionGranted(PERMISSION_CODE)
    private void requiresPermission()
    {
        if (permissionUtils.hasAllBasePermissions(this))
        {

        } else
        {
            EasyPermissions.requestPermissions(
                    this,
                    "需要权限",
                    PERMISSION_CODE,
                    PERMISSION_GROUP);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms)
    {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms)
    {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms) || perms.size() != 0)
        {
            new AppSettingsDialog.Builder(mActivity).setTitle("提示").setRationale("缺失权限了").build().show();
        }

    }

    private void showAlertDialog()
    {
        new XPopup.Builder(mActivity).asConfirm("我是标题", "缺少权限，不能运行",
                "", "确定",
                new OnConfirmListener()
                {
                    @Override
                    public void onConfirm()
                    {
                        mActivity.finish();
                    }
                }, null, false).show();
    }
}
