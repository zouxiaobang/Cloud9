package com.cloud9.cloud9.util.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public class PermissionManager {
    private static int sRequestCode = -1;

    private static OnPermissionListener sListener;

    public abstract static class RationaleHandler {
        private Context mContext;
        private int mRequestCode;
        private String[] mPermissions;

        protected abstract void showRationale();

        void showRationale(Context context, int requestCode, String[] permissions) {
            mContext = context;
            mRequestCode = requestCode;
            mPermissions = permissions;
            showRationale();
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void requestAgain() {
            ((Activity) mContext).requestPermissions(mPermissions, mRequestCode);
        }
    }


    /**
     * 申请权限方法
     *
     * @param context     上下文
     * @param requestCode 请求码 -- 对应回调方法的请求码
     * @param permissions -- 要申请的权限集合
     * @param listener    -- 申请权限时的监听器
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermission(Context context, int requestCode,
                                         String[] permissions, OnPermissionListener listener) {
        requestPermission(context, requestCode, permissions, listener, null);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermission(Context context, int requestCode,
                                         String[] permissions, OnPermissionListener listener,
                                         RationaleHandler handler) {
        if (context instanceof Activity) {
            sRequestCode = requestCode;
            sListener = listener;
            String[] deniedPermissions = getDeniedPermission(context, permissions);

            //防止重复申请权限
            if (deniedPermissions.length > 0) {
                boolean rationale = shouldShowRequestPermissionRationale(context, deniedPermissions);

                if (rationale && handler != null) {
                    handler.showRationale(context, requestCode, deniedPermissions);
                } else {
                    ((Activity) context).requestPermissions(deniedPermissions, requestCode);
                }
            } else {
                if (sListener != null) {
                    sListener.onPermissionGranted();
                }
            }
        } else {
            throw new RuntimeException("u have to send the Activity type not a Context");
        }
    }

    /**
     * 申请权限时的回调函数
     *
     * @param context      上下文
     * @param requestCode  请求码
     * @param permissions  要申请的权限集合
     * @param grantResults ...
     */
    public static void onRequestPermissionResult(Activity context, int requestCode,
                                                 String[] permissions, int[] grantResults) {
        if (sRequestCode != -1 && requestCode == sRequestCode) {
            if (sListener != null) {
                //获取没有通过的权限集合
                String[] deniedPermissions = getDeniedPermission(context, permissions);
                if (deniedPermissions.length > 0) {
                    sListener.onPermissionDenied(deniedPermissions);
                } else {
                    sListener.onPermissionGranted();
                }
            }
        }
    }

    /**
     * 获取没有通过的权限集合
     *
     * @param context     上下文
     * @param permissions 要申请的所有权限
     * @return 没有通过的权限集合
     */
    private static String[] getDeniedPermission(Context context, String[] permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }

        return deniedPermissions.toArray(new String[deniedPermissions.size()]);
    }


    /**
     * 是否要显示请求权限的说明提示
     *
     * @param context           上下文
     * @param deniedPermissions 没有通过的权限
     * @return ...
     */
    private static boolean shouldShowRequestPermissionRationale(Context context, String[] deniedPermissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        boolean rationale;

        for (String permission : deniedPermissions) {
            rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);

            //注意这里不能简写为return rationale;
            if (rationale) {
                return true;
            }
        }

        return false;
    }

    public interface OnPermissionListener {
        /**
         * 权限申请成功
         */
        void onPermissionGranted();

        /**
         * 拒绝了一些权限的申请
         *
         * @param deniedPermissions 没有通过的权限
         */
        void onPermissionDenied(String[] deniedPermissions);
    }

}
