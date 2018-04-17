package org.standardmvp.net.function;

import com.google.gson.Gson;

import org.standardmvp.net.core.HttpResponse;
import org.standardmvp.net.exception.ServerException;
import org.standardmvp.utils.LogUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class ServerResultFunction implements Function<HttpResponse, Object> {

    @Override
    public Object apply(@NonNull HttpResponse httpResponse) throws Exception {
        LogUtils.e("http Response ====== " , httpResponse.toString());
        if(200 != httpResponse.getCode()) {
            throw new ServerException(httpResponse.getCode(), httpResponse.getMsg());
        }
        return new Gson().toJson(httpResponse.getData());
    }
}
