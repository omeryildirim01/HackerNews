package com.conntac.hackernews.ui.ItemList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.conntac.hackernews.R;
import com.conntac.hackernews.data.ApiHelper;
import com.conntac.hackernews.data.RequestFactory;
import com.conntac.hackernews.model.AskItem;
import com.conntac.hackernews.model.BaseItem;
import com.conntac.hackernews.model.CommentItem;
import com.conntac.hackernews.model.JobItem;
import com.conntac.hackernews.model.PollItem;
import com.conntac.hackernews.model.PolloptItem;
import com.conntac.hackernews.model.StoryItem;
import com.conntac.hackernews.ui.base.BaseFragment;
import com.conntac.hackernews.ui.main.MainActivity;
import com.conntac.hackernews.util.AnimationUtil;
import com.conntac.hackernews.util.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemListFragment extends BaseFragment implements ItemListContract.View{
    //region Members
    public  static  String TAG =ItemListFragment.class.getSimpleName();
    private ItemListContract.Presenter presenter;
    public List<BaseItem> itemList ;
    ItemAdapter itemAdapter;
    @BindView(R.id.listViewItems) ListView listViewItems;
    @BindView(R.id.toolbarItemList) Toolbar toolbar;
    @BindView(R.id.toolbarListTitleTextView) TextView toolbarTitleTextView;
    public int SelectedIndex;
    public  static String INDEX="INDEX";
    //endregion
    //region Method&Class
    /**
     * constructor Method
     */
    public ItemListFragment() {
     itemList = new ArrayList<>();
    }

    /**
     * Create an instance
     * @param selectedIndex
     * @return
     */
    public  static  ItemListFragment getInstance(int selectedIndex){
        ItemListFragment itemListFragment = new ItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX,selectedIndex);
        itemListFragment.setArguments(bundle);
        return itemListFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Fragment lifecycle method onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_item_list, container, false);
        return  rootView;
    }

    /**
     * Fragment lifecycle method onViewCreated
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
    }

    /**
     * init ui
     */
    @Override
    public void  initView(){
     try {
         listViewItems.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
         SelectedIndex= getArguments().getInt(INDEX,0);
         if (itemList== null) itemList = new ArrayList<>();
         itemAdapter = new ItemAdapter(itemList,presenter);
         listViewItems.setAdapter(itemAdapter);
         listViewItems.setSelection(SelectedIndex);
         toolbarTitleTextView.setText(R.string.top_stories);
     }catch (Exception ex){
         Log.e(ex.toString(),ex.getMessage());
     }
    }

    /**
     * Fragment lifecycle method onAttach
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Fragment lifecycle method onDetach
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * set presenter for ui
     * @param _presenter
     */
    @Override
    public void setPresenter(ItemListContract.Presenter _presenter) {
        this.presenter = _presenter;
    }

    /**
     *  Item Adapter
     */
     class ItemAdapter extends BaseAdapter {
        List<BaseItem> items;
        ItemListContract.Presenter presenter;
        public  ItemAdapter(List<BaseItem> arrayList,ItemListContract.Presenter _presenter) {
            items=arrayList;
            presenter = _presenter;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public BaseItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return items.get(position).getId();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            try{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View rowView;
                rowView = inflater.inflate(R.layout.item_row_layout, parent, false);
                final Button btnDetail = rowView.findViewById(R.id.btnDetails);
                final TextView textView = rowView.findViewById(R.id.textView1);
                final TextView textView2 = rowView.findViewById(R.id.textView2);
                final LinearLayout linearLayoutProgress = rowView.findViewById(R.id.linearLayoutProgress);
                final BaseItem selectedItem = getItem(position);
                if (selectedItem.getType()!=null)
                    textView2.setText(selectedItem.getType().toString());
                btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onClickItemDetail(selectedItem);
                        ((MainActivity)getActivity()).SelectedIndex = position;
                    }
                });
                // item detail request response
                Response.Listener<BaseItem> listener = new Response.Listener<BaseItem>() {
                    @Override
                    public void onResponse(BaseItem response) {
                        // Check response status
                        if (response!=null)
                        {    // if  item is Story , expacting normally Story item
                            if (response instanceof  StoryItem && selectedItem instanceof StoryItem) {
                                StoryItem storyItem = (StoryItem) response;
                                ((StoryItem)selectedItem).setUrl(storyItem.getUrl());
                                ((StoryItem)selectedItem).setBy(storyItem.getBy());
                                ((StoryItem)selectedItem).setDescendants(storyItem.getDescendants());
                                ((StoryItem)selectedItem).setKids(storyItem.getKids());
                                ((StoryItem)selectedItem).setScore(storyItem.getScore());
                                ((StoryItem)selectedItem).setTime(storyItem.getTime());
                                ((StoryItem)selectedItem).setTitle(storyItem.getTitle());
                                ((StoryItem)selectedItem).setType(storyItem.getType());
                                textView.setText( "By: "+((StoryItem)selectedItem).getBy());
                                linearLayoutProgress.setVisibility(View.INVISIBLE);
                                btnDetail.setVisibility(View.VISIBLE);
                                textView2.setText("story");
                            }// if base item is JobItem , update view and adapter
                            else if (response instanceof JobItem) {
                                JobItem jobItem = (JobItem) response;
                                items.remove(position);
                                items.add(position,jobItem);
                                notifyDataSetChanged();
                                textView.setText("By: "+ jobItem.getBy());
                                linearLayoutProgress.setVisibility(View.INVISIBLE);
                                btnDetail.setVisibility(View.VISIBLE);
                                textView2.setText("job");

                            }// if base item is PollItem , update view and adapter
                            else if (response instanceof PollItem) {
                                PollItem pollItem = (PollItem) response;
                                items.remove(position);
                                items.add(position,pollItem);
                                notifyDataSetChanged();
                                textView.setText("By: "+ pollItem.getBy());
                                linearLayoutProgress.setVisibility(View.INVISIBLE);
                                btnDetail.setVisibility(View.VISIBLE);
                                textView2.setText("poll");

                            }
                            // if base item is PolloptItem , update view and adapter
                            else if (response instanceof PolloptItem) {
                                PolloptItem polloptItem = (PolloptItem) response;
                                items.remove(position);
                                items.add(position,polloptItem);
                                notifyDataSetChanged();
                                textView.setText("By: "+ polloptItem.getBy());
                                linearLayoutProgress.setVisibility(View.INVISIBLE);
                                btnDetail.setVisibility(View.VISIBLE);
                                textView2.setText("pollopt");

                            }
                            // if base item is Ask , update view and adapter
                            else if (response instanceof AskItem) {
                                AskItem askItem = (AskItem) response;
                                items.remove(position);
                                items.add(position,askItem);
                                notifyDataSetChanged();
                                textView.setText("By: "+ askItem.getBy());
                                linearLayoutProgress.setVisibility(View.INVISIBLE);
                                btnDetail.setVisibility(View.VISIBLE);
                                textView2.setText("ask");

                            }
                            // if base item is CommentItem , update view and adapter
                            else if (response instanceof CommentItem) {
                                CommentItem commentItem = (CommentItem) response;
                                items.remove(position);
                                items.add(position,commentItem);
                                notifyDataSetChanged();
                                textView.setText("By: "+ commentItem.getBy());
                                linearLayoutProgress.setVisibility(View.INVISIBLE);
                                btnDetail.setVisibility(View.VISIBLE);
                                textView2.setText("comment");

                            }
                            else {
                                //TODO:
                                // Different record type
                                    Toast.makeText(getActivity(),"Not Found :" + response.getId(),Toast.LENGTH_LONG).show();
                                }
                        }else{
                            //TODO:
                            // Different record type
                            Toast.makeText(getActivity(),"Not Found :" + response.getId(),Toast.LENGTH_LONG).show();
                        }
                    }
                };
                // Check item situations
                if (selectedItem instanceof StoryItem)
                {
                    // if item is Story
                    StoryItem storyItem = (StoryItem)selectedItem;
                    if (!Tools.isNullOrBlank(storyItem.getBy()))
                        textView.setText("By: "+storyItem.getBy());
                    if (storyItem.getBy() ==null)
                    {
                        storyItem.setBy("");
                        linearLayoutProgress.setVisibility(View.VISIBLE);
                        btnDetail.setVisibility(View.INVISIBLE);
                    }else{
                        linearLayoutProgress.setVisibility(View.INVISIBLE);
                        btnDetail.setVisibility(View.VISIBLE);
                    }
                }else if (selectedItem instanceof JobItem)
                {
                    // if item is Job
                    JobItem jobItem = (JobItem)selectedItem;
                    if (!Tools.isNullOrBlank(jobItem.getBy()))
                        textView.setText("By: "+jobItem.getBy());
                    if (jobItem.getBy() ==null)
                    {
                        jobItem.setBy("");
                        linearLayoutProgress.setVisibility(View.VISIBLE);
                        btnDetail.setVisibility(View.INVISIBLE);
                    }else{
                        linearLayoutProgress.setVisibility(View.INVISIBLE);
                        btnDetail.setVisibility(View.VISIBLE);
                    }
                }else if (selectedItem instanceof PollItem)
                {
                    // if item is Poll
                    PollItem pollItem = (PollItem)selectedItem;
                    if (!Tools.isNullOrBlank(pollItem.getBy()))
                        textView.setText("By: "+pollItem.getBy());
                    if (pollItem.getBy() ==null)
                    {
                        pollItem.setBy("");
                        linearLayoutProgress.setVisibility(View.VISIBLE);
                        btnDetail.setVisibility(View.INVISIBLE);
                    }else{
                        linearLayoutProgress.setVisibility(View.INVISIBLE);
                        btnDetail.setVisibility(View.VISIBLE);
                    }
                }
                // if item details are null , create a request to get detail
                if (selectedItem.getType() == null){
                    // Requesting to get item details from API
                        RequestFactory.getInstance(getActivity()).getRequestQueue().add( ApiHelper.getBaseItem(getItem(position).getId(),listener,getDetailFailed()));
                }
                AnimationUtil.startBounceAnim(rowView);
                return rowView;
            }catch (Exception ex)
            {
                //TODO:
                Log.e(ex.toString(),ex.getMessage());
                return  null;
            }
        }

        private Response.ErrorListener getDetailFailed() {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                    Log.e(error.toString(),error.getMessage());
                }
            };
        }

    }

    //endregion Method&Class
}
