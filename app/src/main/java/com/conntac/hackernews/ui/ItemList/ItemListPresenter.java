package com.conntac.hackernews.ui.ItemList;


import com.conntac.hackernews.model.BaseItem;
import com.conntac.hackernews.model.StoryItem;
import com.conntac.hackernews.ui.base.BaseActivity;
import com.conntac.hackernews.ui.main.MainActivity;


/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class ItemListPresenter implements ItemListContract.Presenter{
    public static String TAG = ItemListPresenter.class.getSimpleName();
    BaseActivity activity;
    ItemListContract.View view;
    public ItemListPresenter(BaseActivity ctx, ItemListContract.View _view)
    {
        activity = ctx;
        view =_view;
    }

    @Override
    public void onClickItemDetail(BaseItem item) {
        if (activity instanceof MainActivity)
        {
            ((MainActivity)activity).showItemDetailFragment(item);
        }
    }

}
