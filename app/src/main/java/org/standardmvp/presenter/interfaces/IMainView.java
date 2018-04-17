package org.standardmvp.presenter.interfaces;

import org.standardmvp.base.IBaseView;
import org.standardmvp.model.UserInfo;

/**
 * MainActivity分配的view接口
 */

public interface IMainView extends IBaseView {

    void showData(UserInfo data);

    void showError(String errorMsg);
}
