package org.standardmvp.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.standardmvp.base.BasePresenter;
import org.standardmvp.model.UserInfo;
import org.standardmvp.net.HttpCenter;
import org.standardmvp.net.core.HttpObserver;
import org.standardmvp.net.exception.ApiException;
import org.standardmvp.net.function.RxActionFunction;
import org.standardmvp.presenter.interfaces.IMainView;
import org.standardmvp.view.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * MainActivity分配的presenter
 */

public class MainPresenter extends BasePresenter<IMainView,MainActivity> {

    //接口请求标识
    private final static String getIpInfoTag = "getIpInfo";

    public MainPresenter(IMainView view , MainActivity activity) {
        super(view, activity);
    }


    //获取ip详细信息函数
    public void createUser(String phone) {
        if(TextUtils.isEmpty(phone)) {
            getView().showError("手机号不能为空!");
            return;
        }

        HttpObserver<Object> observer = new HttpObserver<Object>(getIpInfoTag) {
            @Override
            protected void onFailure(ApiException e) {
                getView().dismissProgress();
                getView().showError(e.getMsg());
            }

            @Override
            protected void onSuccess(Object o) {
                getView().dismissProgress();
                UserInfo data = new Gson().fromJson(o.toString(),UserInfo.class);
                getView().showData(data);
            }

            @Override
            protected void onStart() {
                getView().showProgress();
            }
        };

        Map<String,Object> params = new HashMap<>();
        params.put("key","00d91e8e0cca2b76f515926a36db68f5");
        params.put("phone",phone);
        params.put("passwd","123456");
        //调用model层访问网络请求服务器
        HttpCenter.getInstance().request(HttpCenter.getInstance().getApi().createUser(params),getActivity(),observer);
    }


    //手动取消请求(一般不需要，网络请求跟生命周期已经绑定)
    public void cancel(String tag) {
        RxActionFunction.getInstance().cancel(tag);
    }
}
