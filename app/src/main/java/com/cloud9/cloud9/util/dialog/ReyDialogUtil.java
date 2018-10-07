package com.cloud9.cloud9.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;

import com.cloud9.cloud9.R;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
/**
 * author: xb.Zou
 * date: 2018/9/3 0003
 **/
public class ReyDialogUtil {
    private ReyDialogUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * @param mDialog dialog diamis
     *                立即关闭，无关闭动画
     */
    public static void dismiss(Dialog mDialog) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.clearContent();
            mDialog.dismissImmediately();
        }
    }

    /**
     *  在底部的dialog
     * @param mDialog
     */
    public static void dismiss(BottomSheetDialog mDialog) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismissImmediately();
        }
    }

    /**
     * @param mDialog
     * @param dismissListener dialog消失后回调
     */
    public static void dismiss(Dialog mDialog, DialogInterface.OnDismissListener dismissListener) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.clearContent();
            mDialog.dismiss();
            mDialog.setOnDismissListener(dismissListener);
        }
    }

    /**
     * @param context
     * @return 进度条dialog
     */
    public static Dialog createLoadingDialog(Context context) {
        Dialog mDialog = new Dialog.Builder()
                .contentView(R.layout.dialog_loading)
                .style(R.style.DialogStyle)
                .build(context);
        mDialog.setCancelable(false);
        mDialog.elevation(0); //去除阴影
        mDialog.backgroundColor(ContextCompat.getColor(context, R.color.transparent)); //背景透明
        return mDialog;
    }

    /**
     * 修改密码dialog
     *
     * @param context
     * @param clickListener
     * @return
     */
    public static Dialog createModifyPwdDialog(@NonNull Context context, final View.OnClickListener clickListener) {
        final Dialog mDialog = new Dialog.Builder()
                .contentView(R.layout.dialog_modify_pwd)
                .style(R.style.DialogStyle)
                .build(context);
        mDialog.elevation(0); //去除阴影


        final EditText oldPwd = (EditText) mDialog.findViewById(R.id.dialog_et_old_pwd);
        final EditText newPwd = (EditText) mDialog.findViewById(R.id.dialog_et_new_pwd);

        //提交
        Button commit = (Button) mDialog.findViewById(R.id.dialog_btn_commit);
        if (clickListener != null) {
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(0 + "", oldPwd.getText().toString().trim());
                    bundle.putString(1 + "", newPwd.getText().toString().trim());
                    v.setTag(bundle);
                    clickListener.onClick(v);
                }
            });
        }

        mDialog.setCancelable(true);//可点空白取消
        return mDialog;
    }

    /**
     * 删除招聘信息dialog
     *
     * @param context
     * @param clickListener
     * @return
     */
    public static Dialog createDeleteAdDialog(@NonNull Context context, final View.OnClickListener clickListener) {
        final Dialog mDialog = new Dialog.Builder()
                .contentView(R.layout.dialog_delete_ad)
                .style(R.style.DialogStyle)
                .build(context);
        mDialog.elevation(0); //去除阴影


        final Button btnDelete = (Button) mDialog.findViewById(R.id.dialog_btn_delete);
        final Button btnCancel = (Button) mDialog.findViewById(R.id.dialog_btn_cancel);

        if (clickListener != null) {
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(v);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss(mDialog);
                }
            });
        }

        mDialog.setCancelable(true);//可点空白取消
        return mDialog;
    }
}
