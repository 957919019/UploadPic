package com.ksc.uploadpicdemo.utils;

import android.Manifest;
import android.content.Context;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by 康盛超 on 2019/1/25 at ShengBoYing Company.
 */
public class PermissionUtils
{
    /**
     * 必须的权限
     */
    public static final int PERMISSION_CODE = 0x000123;
    public static final String[] PERMISSION_GROUP =
            {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };

    /**
     * 获取所有权限
     *
     * @param context
     * @return
     */
    public boolean hasAllBasePermissions(Context context)
    {
        return EasyPermissions.hasPermissions(context, PERMISSION_GROUP);
    }
}