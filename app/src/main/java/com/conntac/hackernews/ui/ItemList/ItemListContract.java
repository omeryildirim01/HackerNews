package com.conntac.hackernews.ui.ItemList;

import com.conntac.hackernews.model.BaseItem;
import com.conntac.hackernews.ui.base.BasePresenter;
import com.conntac.hackernews.ui.base.BaseView;


/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public interface ItemListContract {
    interface View extends BaseView<Presenter> {
        void  initView();
    }

    interface Presenter extends BasePresenter {
        void onClickItemDetail(BaseItem item);
    }
}
