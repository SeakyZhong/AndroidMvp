package org.standardmvp.net.api;

import org.standardmvp.net.core.HttpResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * http接口
 * Created by Seaky on 2017/9/22.
 */

public interface ApiService {

    public static final String BASE_URL = "https://www.apiopen.top/";

    @GET("createUser")
    Observable<HttpResponse> createUser(@QueryMap Map<String,Object> params);
}
