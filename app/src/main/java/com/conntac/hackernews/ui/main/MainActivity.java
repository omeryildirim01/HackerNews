package com.conntac.hackernews.ui.main;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.conntac.hackernews.R;
import com.conntac.hackernews.data.ApiHelper;
import com.conntac.hackernews.data.RequestFactory;
import com.conntac.hackernews.model.BaseItem;
import com.conntac.hackernews.model.StoryItem;
import com.conntac.hackernews.ui.ItemDetail.ItemDetailContract;
import com.conntac.hackernews.ui.ItemDetail.ItemDetailFragment;
import com.conntac.hackernews.ui.ItemDetail.ItemDetailPresenter;
import com.conntac.hackernews.ui.ItemList.ItemListContract;
import com.conntac.hackernews.ui.ItemList.ItemListFragment;
import com.conntac.hackernews.ui.ItemList.ItemListPresenter;
import com.conntac.hackernews.ui.base.BaseActivity;
import com.conntac.hackernews.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    //region Members
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ItemListFragment itemListFragment;
    ItemListPresenter itemListPresenter;

    public List<BaseItem> itemList ;
    public int SelectedIndex;
    //endregion
    //region Method&Class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SelectedIndex =0;
        initView();
    }

    /**
     * Initialize the view
     */
    @Override
    public void initView() {
      try{
          itemList = new ArrayList<>();
          fragmentManager = getSupportFragmentManager();
          itemListFragment = new ItemListFragment();
          itemListPresenter = new ItemListPresenter(this,itemListFragment);
          itemListFragment.setPresenter(itemListPresenter);
          new RequestTask().execute();
      }catch (Exception e){
          Log.e(e.toString(),e.getMessage());
      }
    }

    /**
     * To show item list
     */
    @Override
    public void showItemListFragment() {
        try{
        itemListFragment = ItemListFragment.getInstance(SelectedIndex);
        itemListPresenter = new ItemListPresenter(this,itemListFragment);
        itemListFragment.setPresenter(itemListPresenter);
        itemListFragment.itemList=itemList;
        onFragmentReplace(itemListFragment);
      }
        catch (Exception e) {
        e.printStackTrace();
        Log.e(e.toString(), e.getMessage());
      }
    }
    /**
     * To show item detail
     */
    @Override
    public void showItemDetailFragment(BaseItem item) {
        try{
            ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
            ItemDetailPresenter itemDetailPresenter = new ItemDetailPresenter(this,itemDetailFragment,item);
            itemDetailFragment.setPresenter(itemDetailPresenter);
            onFragmentReplace(itemDetailFragment);
         }
         catch (Exception e) {
            e.printStackTrace();
            Log.e(e.toString(), e.getMessage());
         }
    }

    /**
     * On back button pressed event
     */
    @Override
    public void onBackPressed() {
      try{
          // if showing main menu, back to exit
          if (itemListFragment.isVisible()){
              new AlertDialog.Builder(this)
                      .setIcon(android.R.drawable.ic_dialog_alert)
                      .setTitle(getString(R.string.closing_activity))
                      .setMessage(getString(R.string.close_activity_message))
                      .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              android.os.Process.killProcess(android.os.Process.myPid());
                              System.exit(1);
                          }
                      })
                      .setNegativeButton(getString(R.string.no), null)
                      .show();
          }else {
              showItemListFragment();
          }

      }catch (Throwable e){
          Log.e(e.toString(),e.getMessage());
      }
    }

    /**
     * To change fragments on shown
     * @param _fragment
     */
    @Override
    public void onFragmentReplace(BaseFragment _fragment) {
        try {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom);
            fragmentTransaction.replace(R.id.frameLayout, _fragment, _fragment.getClass().getName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(e.toString(), e.getMessage());
        }
    }

    /**
     * Set up presenter for
     * @param presenter
     */
    @Override
    public void setPresenter(ItemListContract.Presenter presenter) {
    }

    /**
     * Executor task to get data via internet
     */
    protected class RequestTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            RequestFactory.getInstance(getApplication()).getRequestQueue().add( ApiHelper.getDummyObjectArray(getTopStoriesSuccess(),getTopStoriesFailed()));
            return null;
        }
    }

    /**
     * Api request response Listener
     * @return
     */
    private Response.ErrorListener getTopStoriesFailed() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Api request response Listener
     * @return
     */
    private Response.Listener<ArrayList<Long>> getTopStoriesSuccess() {
        return new Response.Listener<ArrayList<Long>>() {
            @Override
            public void onResponse(ArrayList<Long> response) {
                if (response!=null)
                {
                    for (Long a :response) {
                        StoryItem item = new StoryItem();
                        item.setId(a);
                        itemList.add(item);
                    }
                    showItemListFragment();
                }
            }
        };
    }

    //endregion Method&Class
}