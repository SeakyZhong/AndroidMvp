package org.standardmvp.net;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.standardmvp.net.api.ApiService;
import org.standardmvp.net.core.HttpObserver;
import org.standardmvp.net.core.HttpResponse;
import org.standardmvp.net.function.HttpResultFunction;
import org.standardmvp.net.function.ServerResultFunction;
import org.standardmvp.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * http请求核心类
 * Created by Seaky on 2017/9/22.
 */

public class HttpCenter {

    private volatile static HttpCenter mInstance;
    private volatile ApiService mApi;


    public static HttpCenter getInstance() {
        if (mInstance == null) {
            synchronized (HttpCenter.class) {
                mInstance = new HttpCenter();
            }
        }
        return mInstance;
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(LogInterceptor())
                .build();
    }

    private HttpLoggingInterceptor LogInterceptor(){
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("Http Request  =====  ", "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    private Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public ApiService getApi() {
        if(null == mApi) {
            mApi = retrofit().create(ApiService.class);
        }
        return mApi;
    }


    /**
     * 业务层调用统一入口
     * @param observable   接口实例
     * @param mActivity    上下文
     * @param httpObserver 订阅回调
     */
    public void request(Observable<HttpResponse> observable ,
                        RxAppCompatActivity mActivity, HttpObserver<Object> httpObserver) {
        observable.map(new ServerResultFunction())
                .onErrorResumeNext(new HttpResultFunction<>())
                .compose(mActivity.bindUntilEvent(ActivityEvent.DESTROY))
                .compose(setThread())
                .subscribe(httpObserver);
    }


    /**
     * 业务层调用统一入口
     * @param observable   接口实例
     * @param mFragment    上下文
     * @param httpObserver 订阅回调
     */
    public void request(Observable<HttpResponse> observable ,
                        RxFragment mFragment, HttpObserver<Object> httpObserver) {
        observable.map(new ServerResultFunction())
                .onErrorResumeNext(new HttpResultFunction<>())
                .compose(mFragment.bindUntilEvent(FragmentEvent.DESTROY))
                .compose(setThread())
                .subscribe(httpObserver);
    }
}
