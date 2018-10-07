package com.cloud9.cloud9.mvp.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloud9.cloud9.R;
import com.cloud9.cloud9.base.BaseActivity;
import com.cloud9.cloud9.util.logger.Logger;
import com.cloud9.cloud9.util.logger.Toaster;

import butterknife.BindView;
import butterknife.OnClick;

public class SampleActivity extends BaseActivity<SamplePresenter> implements ISampleView {

    @BindView(R.id.btn_cal)
    Button mBtnCal;
    @BindView(R.id.tv_after_cal)
    TextView mTvAfterCal;
    @BindView(R.id.btn_baidu)
    Button mBtnBaidu;
    @BindView(R.id.btn_insert)
    Button mBtnInsert;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.btn_update)
    Button mBtnUpdate;
    @BindView(R.id.btn_query)
    Button mBtnQuery;

    @Override
    protected Integer setLayout() {
        return R.layout.activity_sample;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initPresenter() {
        mPresenter = new SamplePresenter();
        mPresenter.attachView(this);
    }





    @OnClick(R.id.btn_cal)
    public void cal(){
        Logger.i("cal ... ");
        mPresenter.realCal(12);
    }

    @OnClick(R.id.btn_baidu)
    public void connectBaidu(){
        Logger.i("connect baidu web ... ");
        mPresenter.connectBaidu(this);
    }

    @OnClick(R.id.btn_insert)
    public void insertData(){
        Logger.i("insert data ... ");
        mPresenter.insertData();
    }

    @OnClick(R.id.btn_delete)
    public void deleteData(){
        Logger.i("delete data ... ");
        mPresenter.deleteData();
    }

    @OnClick(R.id.btn_update)
    public void updateData(){
        Logger.i("update data ... ");
        mPresenter.updateData();
    }

    @OnClick(R.id.btn_query)
    public void queryData(){
        Logger.i("query data ... ");
        mPresenter.queryData();
    }






    /**
     * 由SamplePresenter返回的信息
     * @param flag 成功回调的类型
     * @param msg 详细信息
     */
    @Override
    public void onSuccess(int flag, String msg) {
        super.onSuccess(flag, msg);

        switch (flag){
            case 1:
                mTvAfterCal.setText(msg);
                break;
            case 200:
                Toaster.showLong(msg);
                break;
            default:
               break;
        }

    }

    @Override
    public void onError(int code, String errorMsg) {
        super.onError(code, errorMsg);

        Logger.e(code + " : " + errorMsg);
        Toaster.showLong(code + " : " + errorMsg);
    }

    @Override
    public void displayDbResult(String result) {
        Toaster.showLong(result);
    }
}
