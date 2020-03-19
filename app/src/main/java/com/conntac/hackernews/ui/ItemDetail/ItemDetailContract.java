package com.conntac.hackernews.ui.ItemDetail;

import com.conntac.hackernews.model.BaseItem;
import com.conntac.hackernews.ui.base.BaseActivity;
import com.conntac.hackernews.ui.base.BasePresenter;
import com.conntac.hackernews.ui.base.BaseView;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public interface ItemDetailContract {
    interface View extends BaseView<ItemDetailContract.Presenter> {

        void initView();
    }

    interface Presenter extends BasePresenter {
        BaseItem getSelectedItem();
        BaseActivity getActivity();
        ItemDetailContract.View getView();
    }
}
