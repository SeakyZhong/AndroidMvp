package org.standardmvp.view.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


import org.standardmvp.R;
import org.standardmvp.base.BaseActivity;
import org.standardmvp.model.UserInfo;
import org.standardmvp.presenter.MainPresenter;
import org.standardmvp.presenter.interfaces.IMainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainView {

    @BindView(R.id.et_demo) EditText mEtDemo;
    @BindView(R.id.tv_demo) TextView mTvDemo;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this,this);
    }


    @OnClick(R.id.btn_demo)
    public void clickBtn(){
        String phone = mEtDemo.getText().toString();
        presenter.createUser(phone);
    }

    @Override
    public void showData(UserInfo data) {
        mTvDemo.setText("注册时间 : " + data.getCreateTime() + " 密码 : " + data.getPasswd());
    }

    @Override
    public void showError(String errorMsg) {
        mTvDemo.setText(errorMsg);
    }

    @Override
    public void showProgress() {
        mTvDemo.setText("请求中...");
    }

    @Override
    public void dismissProgress() {

    }
}
