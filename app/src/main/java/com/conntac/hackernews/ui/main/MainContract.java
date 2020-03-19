package com.conntac.hackernews.ui.main;

import com.conntac.hackernews.model.BaseItem;
import com.conntac.hackernews.ui.ItemList.ItemListContract;
import com.conntac.hackernews.ui.base.BaseFragment;
import com.conntac.hackernews.ui.base.BasePresenter;
import com.conntac.hackernews.ui.base.BaseView;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public interface MainContract {
    interface View extends BaseView<ItemListContract.Presenter> {
        void showItemListFragment();
        void showItemDetailFragment(BaseItem item);
        void onFragmentReplace(BaseFragment _fragment);
    }

    interface Presenter extends BasePresenter {

    }
}
