package com.conntac.hackernews.ui.ItemDetail;


import com.conntac.hackernews.model.BaseItem;
import com.conntac.hackernews.ui.base.BaseActivity;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class ItemDetailPresenter implements ItemDetailContract.Presenter {
    private BaseItem SelectedItem;
    private BaseActivity activity;
    private ItemDetailContract.View view;
    public  ItemDetailPresenter(BaseActivity context,ItemDetailContract.View v,BaseItem s){
       this.SelectedItem=s;
       this.activity=context;
       this.view=v;
    }

    @Override
    public BaseItem getSelectedItem() {
        return this.SelectedItem;
    }

    @Override
    public BaseActivity getActivity() {
        return this.activity;
    }

    @Override
    public ItemDetailContract.View getView() {
        return this.view;
    }
}
