package org.standardmvp.base;

/**
 * 同步Activity生命周期监听
 * Created by Seaky on 2017/9/21.
 */

public interface IBaseLifeCycle {
    //暂时只用到onDestroy 可根据业务需要扩展增加 在Activity销毁同时解除Mvp组件之间的绑定关系
    void onDestroy();
}
