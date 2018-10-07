package com.cloud9.cloud9.widget.loading;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.cloud9.cloud9.R;
import com.cloud9.cloud9.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class CloudLoader {
    private static final int LOADER_SIZE_SCALE = 6;
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    /**
     * 默认的样式
     */
    private static final String DEFAULT_DIALOG = LoaderStyle.BallClipRotatePulseIndicator.name();
    private static final String TAG = "LatteLoader";

    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(context, type);
        Log.d(TAG, "showLoading: av loader == " + avLoadingIndicatorView);

        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            //设置偏移量
            lp.height = lp.height + lp.height / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }

        LOADERS.add(dialog);

        dialog.show();
    }


    /**
     * 使用默认的样式来设置加载组件
     */
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_DIALOG);
    }

    public static void showLoading(Context context, Enum<LoaderStyle> style) {
        showLoading(context, style.name());
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null && dialog.isShowing()) {
                //使用cancel方法是因为cancel方法执行的时候会回调onCancel方法
                //可以在取消dialog的时候做一些处理
                dialog.cancel();
            }
        }
    }
}
