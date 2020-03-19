package com.conntac.hackernews.ui.ItemDetail;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.conntac.hackernews.util.Tools;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemDetailFragment extends BaseFragment implements ItemDetailContract.View {
    //region Members
    public static String TAG = ItemDetailFragment.class.getSimpleName();
    private ItemDetailContract.Presenter presenter;
    ArrayList<BaseItem> kidList;
    DetailAdapter detailAdapter;
    @BindView(R.id.txtLabel1) TextView txtLabel1;
    @BindView(R.id.txtLabel2) TextView txtLabel2;
    @BindView(R.id.txtLabel3) TextView txtLabel3;
    @BindView(R.id.txtLabel4) TextView txtLabel4;
    @BindView(R.id.txtLabel5) TextView txtLabel5;
    @BindView(R.id.txtLabel6) TextView txtLabel6;
    @BindView(R.id.txtLabel7) TextView txtLabel7;
    @BindView(R.id.toolbarDetail) Toolbar toolbar;
    @BindView(R.id.toolbarTitleTextView) TextView txtToolbarTitle;
    @BindView(R.id.btnDetailsBack) ImageButton btnBack;
    @BindView(R.id.rDetails) RecyclerView recyclerView;
    //endregion
    //region Method&Class

    /**
     * Constructor Method
     */
    public ItemDetailFragment() {
        kidList = new ArrayList<>();
    }

    /**
     * Fragment lifecycle method onCreate
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
       try {
           View rootView =inflater.inflate(R.layout.fragment_item_detail, container, false);
           return  rootView;
       }catch (Throwable e)
       {
           //TODO:
           Log.e(e.toString(),e.getMessage());
           return  null;
       }
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
     * getter setter method for presenter
     * @return
     */
    public ItemDetailContract.Presenter getPresenter() {
        return presenter;
    }

    /**
     * Set presenter for ui
     * @param presenter
     */
    @Override
    public void setPresenter(ItemDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * get toolbar by fragment
     * @return
     */
    private Toolbar getToolbar()
    {
        return toolbar;
    }

    /**
     * Initialize ui
     */
    @Override
    public void initView() {

       try{
           txtLabel7.setText("Type: "+ presenter.getSelectedItem().getType().toString());
           switch (presenter.getSelectedItem().getType()){
               case story:
                   StoryItem storyItem =(StoryItem) presenter.getSelectedItem();
                   txtLabel1.setText("By    : "+ storyItem.getBy());
                   txtLabel2.setText("Title : "+ storyItem.getTitle());
                   txtLabel3.setText("Score : "+ String.valueOf(storyItem.getScore()));
                   txtLabel6.setText("Time : "+ Tools.ConvertToDateTimeString(storyItem.getTime()));
                   txtLabel5.setText("URL    : "+ storyItem.getUrl());
                   txtLabel4.setText("Descendants : "+ storyItem.getDescendants());
                   txtToolbarTitle.setText("Story Details");
                   break;
               case ask:
                   AskItem askItem =(AskItem) presenter.getSelectedItem();
                   txtLabel1.setText("By: "+ askItem.getBy());
                   txtLabel2.setText("Title : "+ askItem.getTitle());
                   txtLabel3.setText("Score : "+ String.valueOf(askItem.getScore()));
                   txtLabel6.setText("Time : "+ Tools.ConvertToDateTimeString(askItem.getTime()));
                   txtLabel5.setText("URL    : "+ askItem.getUrl());
                   txtLabel4.setText("Descendants : "+ askItem.getDescendants());
                   txtToolbarTitle.setText("Ask Details");
                   break;
               case job:
                   JobItem jobItem =(JobItem) presenter.getSelectedItem();
                   txtLabel1.setText("By: "+ jobItem.getBy());
                   txtLabel2.setText("Title : "+ jobItem.getTitle());
                   txtLabel3.setText("Score : "+ String.valueOf(jobItem.getScore()));
                   txtLabel6.setText("Time : "+ Tools.ConvertToDateTimeString(jobItem.getTime()));
                   txtLabel5.setText("URL    : "+ jobItem.getUrl());
                   txtToolbarTitle.setText("Job Details");
                   break;
               case poll:
                   PollItem pollItem =(PollItem) presenter.getSelectedItem();
                   txtLabel1.setText("By: "+ pollItem.getBy());
                   txtLabel2.setText("Title : "+ pollItem.getTitle());
                   txtLabel3.setText("Score : "+ String.valueOf(pollItem.getScore()));
                   txtLabel6.setText("Time : "+ Tools.ConvertToDateTimeString(pollItem.getTime()));
                   txtLabel5.setText("Score    : "+ String.valueOf(pollItem.getScore()));
                   txtLabel4.setText("Descendants : "+ pollItem.getDescendants());
                   txtToolbarTitle.setText("Poll Details");
                   break;
               case comment:
                   CommentItem commentItem =(CommentItem) presenter.getSelectedItem();
                   txtLabel1.setText("By: "+ commentItem.getBy());
                   txtLabel2.setText("Text : "+ commentItem.getText());
                   txtLabel3.setText("Time : "+ Tools.ConvertToDateTimeString(commentItem.getTime()));
                   txtToolbarTitle.setText("Comment Details");
                   break;
               case pollopt:
                   PolloptItem polloptItem =(PolloptItem) presenter.getSelectedItem();
                   txtLabel1.setText("By: "+ polloptItem.getBy());
                   txtLabel2.setText("Poll : "+ String.valueOf(polloptItem.getPoll()));
                   txtLabel3.setText("Score : "+ String.valueOf(polloptItem.getScore()));
                   txtLabel5.setText("Time : "+ Tools.ConvertToDateTimeString(polloptItem.getTime()));
                   txtLabel4.setText("Score    : "+ String.valueOf(polloptItem.getScore()));
                   txtToolbarTitle.setText("Pollopt Details");

                   break;
               default:
                   break;
           }
           detailAdapter = new DetailAdapter(getContext(),kidList);
           recyclerView.setAdapter(detailAdapter);
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
           linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
           recyclerView.setLayoutManager(linearLayoutManager);
           new RequestTask().execute();

           btnBack.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (getActivity() instanceof  MainActivity)
                       ((MainActivity)getActivity()).showItemListFragment();
               }
           });

       }catch (Exception ex){
           //TODO:
           Log.e(ex.toString(),ex.getMessage());
       }
    }

    /**
     * Item Detail Adapter to list base items on ui
     */
    class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {

        ArrayList<BaseItem> mDetailList;
        LayoutInflater inflater;

        public DetailAdapter(Context context, ArrayList<BaseItem> items) {
            inflater = LayoutInflater.from(context);
            this.mDetailList = items;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            try {
                View view = inflater.inflate(R.layout.item_detail_row_layout, parent, false);
                MyViewHolder holder = new MyViewHolder(view);
                return holder;
            }catch (Exception ex){
                Log.e(ex.toString(),ex.getMessage());
                return null;
            }
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            BaseItem selectedItem = mDetailList.get(position);
            holder.setData(selectedItem, position);
        }
        @Override
        public int getItemCount() {
            return mDetailList.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView detail1, detail2, detail3, detail4, detail5, detail6;
            LinearLayout linearLayoutKids;

            public MyViewHolder(View itemView) {
                super(itemView);
                detail1 = itemView.findViewById(R.id.txtDetail1);
                detail2 = itemView.findViewById(R.id.txtDetail2);
                detail3 = itemView.findViewById(R.id.txtDetail3);
                detail4 = itemView.findViewById(R.id.txtDetail4);
                detail5 = itemView.findViewById(R.id.txtDetail5);
                detail6 = itemView.findViewById(R.id.txtDetail6);
                linearLayoutKids = (LinearLayout)itemView.findViewById(R.id.itemDetaiSubListContainer);
            }

            public void setData(BaseItem selectedDetail, int position) {
                 try{
                     this.detail1.setText(String.valueOf(selectedDetail.getId()));

                     // Check item situations
                     if (selectedDetail instanceof StoryItem) {
                         // if item is Story
                         StoryItem storyItem = (StoryItem)selectedDetail;
                         if (!Tools.isNullOrBlank(storyItem.getBy()))
                             detail1.setText("By: "+storyItem.getBy());

                         detail2.setText("Type: "+storyItem.getType());

                     }
                     else if (selectedDetail instanceof JobItem) {
                         // if item is Job
                         JobItem jobItem = (JobItem)selectedDetail;
                         if (!Tools.isNullOrBlank(jobItem.getBy()))
                             detail1.setText("By: "+jobItem.getBy());

                         detail2.setText("Type: "+jobItem.getType());

                     }
                     else if (selectedDetail instanceof PollItem) {
                         // if item is Poll
                         PollItem pollItem = (PollItem)selectedDetail;
                         if (!Tools.isNullOrBlank(pollItem.getBy()))
                             detail1.setText("By: "+pollItem.getBy());

                         detail2.setText("Type: "+pollItem.getType());

                     }
                     else if (selectedDetail instanceof PolloptItem) {
                         // if item is Pollopt
                         PolloptItem polloptItem = (PolloptItem)selectedDetail;
                         if (!Tools.isNullOrBlank(polloptItem.getBy()))
                             detail1.setText("By: "+polloptItem.getBy());

                         detail2.setText("Type: "+polloptItem.getType());

                     }
                     else if (selectedDetail instanceof CommentItem) {
                         // if item is Poll
                         CommentItem commentItem = (CommentItem)selectedDetail;
                         if (!Tools.isNullOrBlank(commentItem.getBy()))
                             detail1.setText("By: "+commentItem.getBy());

                         detail2.setText("Type: "+commentItem.getType());

                         if (!Tools.isNullOrBlank(commentItem.getText())) {

                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                 detail4.setText(Html.fromHtml("Text: " + commentItem.getText(), Html.FROM_HTML_MODE_COMPACT));
                             } else {
                                 detail4.setText(Html.fromHtml("Text: " + commentItem.getText()));
                             }
                         }
                         detail6.setText("Time: "+ Tools.ConvertToDateTimeString(commentItem.getTime()));

                         if (commentItem.getKids()!=null){
                             for (Long kidId:commentItem.getKids()) {
                                 RequestFactory.getInstance(getActivity()).getRequestQueue().add( ApiHelper.getBaseItem(kidId,getSubDetailKidSuccess(),getDetailKidFailed()));
                             }
                         }

                     }
                     else if (selectedDetail instanceof AskItem) {
                         // if item is Ask
                         AskItem askItem = (AskItem)selectedDetail;
                         if (!Tools.isNullOrBlank(askItem.getBy()))
                             detail1.setText("By: "+askItem.getBy());

                         if (!Tools.isNullOrBlank(askItem.getTitle()))
                             detail3.setText("Title: "+askItem.getTitle());

                         if (!Tools.isNullOrBlank(askItem.getText()))
                             detail4.setText("Text: "+askItem.getText());

                         detail2.setText("Type: "+askItem.getType());
                     }
                 }catch (Exception ex){
                     //TODO:
                     Log.e(ex.toString(),ex.getMessage());
                 }
            }

            /**
             * Api request response Listener
             * @return
             */
            private Response.ErrorListener getSubDetailKidFailed() {
                return new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO:
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                };
            }
            /**
             * Api request response Listener
             * @return
             */
            private Response.Listener<BaseItem> getSubDetailKidSuccess() {
                return new Response.Listener<BaseItem>() {
                    @Override
                    public void onResponse(BaseItem selectedDetail) {
                        try {
                            if (selectedDetail!=null)
                            {
                                View view = inflater.inflate(R.layout.item_detail_kid_layout, null, false);
                                TextView txtKidDetail1= view.findViewById(R.id.txtKidDetail1);
                                TextView txtKidDetail2= view.findViewById(R.id.txtKidDetail2);
                                TextView txtKidDetail3= view.findViewById(R.id.txtKidDetail3);
                                TextView txtKidDetail4= view.findViewById(R.id.txtKidDetail4);
                                linearLayoutKids.addView(view);
                                txtKidDetail1.setText(String.valueOf(selectedDetail.getId()));

                                // Check item situations
                                if (selectedDetail instanceof StoryItem) {
                                    // if item is Story
                                    StoryItem storyItem = (StoryItem)selectedDetail;
                                    if (!Tools.isNullOrBlank(storyItem.getBy()))
                                        txtKidDetail1.setText("By: "+storyItem.getBy());

                                    txtKidDetail2.setText("Type: "+storyItem.getType());

                                }
                                else if (selectedDetail instanceof JobItem) {
                                    // if item is Job
                                    JobItem jobItem = (JobItem)selectedDetail;
                                    if (!Tools.isNullOrBlank(jobItem.getBy()))
                                        txtKidDetail1.setText("By: "+jobItem.getBy());

                                    txtKidDetail2.setText("Type: "+jobItem.getType());

                                }
                                else if (selectedDetail instanceof PollItem) {
                                    // if item is Poll
                                    PollItem pollItem = (PollItem)selectedDetail;
                                    if (!Tools.isNullOrBlank(pollItem.getBy()))
                                        txtKidDetail1.setText("By: "+pollItem.getBy());

                                    txtKidDetail2.setText("Type: "+pollItem.getType());

                                }
                                else if (selectedDetail instanceof PolloptItem) {
                                    // if item is Pollopt
                                    PolloptItem polloptItem = (PolloptItem)selectedDetail;
                                    if (!Tools.isNullOrBlank(polloptItem.getBy()))
                                        txtKidDetail1.setText("By: "+polloptItem.getBy());

                                    txtKidDetail2.setText("Type: "+polloptItem.getType());

                                }
                                else if (selectedDetail instanceof CommentItem) {
                                    // if item is Poll
                                    CommentItem commentItem = (CommentItem)selectedDetail;
                                    if (!Tools.isNullOrBlank(commentItem.getBy()))
                                        txtKidDetail1.setText("By: "+commentItem.getBy());

                                    txtKidDetail2.setText("Type: "+commentItem.getType());

                                    if (!Tools.isNullOrBlank(commentItem.getText())) {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                            txtKidDetail3.setText(Html.fromHtml("Text: " + commentItem.getText(), Html.FROM_HTML_MODE_COMPACT));
                                        } else {
                                            txtKidDetail3.setText(Html.fromHtml("Text: " + commentItem.getText()));
                                        }
                                    }
                                    txtKidDetail4.setText("Time: "+ Tools.ConvertToDateTimeString(commentItem.getTime()));

                                    if (commentItem.getKids()!=null && commentItem.getKids().size()>0){
                                        StringBuilder stringBuilder = new StringBuilder();
                                        for (Long kidId:commentItem.getKids()) {
                                            stringBuilder.append("Kid:");
                                            stringBuilder.append(String.valueOf(kidId));
                                            stringBuilder.append("-");

                                           // RequestFactory.getInstance(getActivity()).getRequestQueue().add( ApiHelper.getBaseItem(kidId,getSubDetailKidSuccess(),getDetailKidFailed()));
                                        }
                                        txtKidDetail2.setText("See Details(" + String.valueOf(commentItem.getKids().size())+")");
                                        txtKidDetail2.setBackground(getResources().getDrawable(R.drawable.bg_rounded));
                                        txtKidDetail2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(getContext(),"Disabled",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                }
                                else if (selectedDetail instanceof AskItem) {
                                    // if item is Ask
                                    AskItem askItem = (AskItem)selectedDetail;
                                    if (!Tools.isNullOrBlank(askItem.getBy()))
                                        txtKidDetail1.setText("By: "+askItem.getBy());

                                    if (!Tools.isNullOrBlank(askItem.getTitle()))
                                        txtKidDetail2.setText("Title: "+askItem.getTitle());

                                    if (!Tools.isNullOrBlank(askItem.getText()))
                                        txtKidDetail3.setText("Text: "+askItem.getText());

                                    txtKidDetail4.setText("Type: "+askItem.getType());
                                }

                            }
                        }catch (Exception ex){
                            //TODO:
                            Log.e(ex.toString(),ex.getMessage());
                        }
                    }
                };
            }

            @Override
            public void onClick(View v) {

            }
        }
    }

    /**
     * Executor task to get data via internet
     */
    protected class RequestTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
             try{
                 // Load item Kids
                 switch (presenter.getSelectedItem().getType()){
                     case story:
                         StoryItem storyItem =(StoryItem) presenter.getSelectedItem();
                         if (storyItem.getKids() !=null && storyItem.getKids().size()>0)
                         for (Long itemId : storyItem.getKids()) {
                             RequestFactory.getInstance(getActivity()).getRequestQueue().add( ApiHelper.getBaseItem(itemId,getDetailKidSuccess(),getDetailKidFailed()));
                         }
                         break;
                     case ask:
                         AskItem askItem =(AskItem) presenter.getSelectedItem();
                         if (askItem.getKids() !=null && askItem.getKids().size()>0)
                         for (Long itemId : askItem.getKids()) {
                             RequestFactory.getInstance(getActivity()).getRequestQueue().add( ApiHelper.getBaseItem(itemId,getDetailKidSuccess(),getDetailKidFailed()));
                         }
                         break;
                     case job:
                         JobItem jobItem =(JobItem) presenter.getSelectedItem();

                         break;
                     case poll:
                         PollItem pollItem =(PollItem) presenter.getSelectedItem();
                         if (pollItem.getKids() !=null && pollItem.getKids().size()>0)
                         for (Long itemId : pollItem.getKids()) {
                             RequestFactory.getInstance(getActivity()).getRequestQueue().add( ApiHelper.getBaseItem(itemId,getDetailKidSuccess(),getDetailKidFailed()));
                         }
                         break;
                     case comment:
                         CommentItem commentItem =(CommentItem) presenter.getSelectedItem();
                         if (commentItem.getKids() !=null && commentItem.getKids().size()>0)
                         for (Long itemId : commentItem.getKids()) {
                             RequestFactory.getInstance(getActivity()).getRequestQueue().add( ApiHelper.getBaseItem(itemId,getDetailKidSuccess(),getDetailKidFailed()));
                         }
                         break;
                     case pollopt:
                         PolloptItem polloptItem =(PolloptItem) presenter.getSelectedItem();

                         break;
                     default:
                         break;
                 }
             }
             catch (Exception ex){
                 Log.e(ex.toString(),ex.getMessage());
             }
            return null;
        }
    }

    /**
     * Api request response Listener
     * @return
     */
    private Response.ErrorListener getDetailKidFailed() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO:
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e(error.toString(),error.getMessage());
            }
        };
    }

    /**
     * Api request response Listener
     * @return
     */
    private Response.Listener<BaseItem> getDetailKidSuccess() {
        return new Response.Listener<BaseItem>() {
            @Override
            public void onResponse(BaseItem response) {
                try {
                         if (response!=null) { kidList.add(response); }
                         detailAdapter.notifyDataSetChanged();
                }catch (Exception ex){
                    //TODO:
                    Log.e(ex.toString(),ex.getMessage());
                }
            }
        };
    }
    //endregion Methods&Class
}
