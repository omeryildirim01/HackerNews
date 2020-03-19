package com.conntac.hackernews.data;

import com.android.volley.Response;
import com.conntac.hackernews.model.AskItem;
import com.conntac.hackernews.model.BaseItem;
import com.conntac.hackernews.model.CommentItem;
import com.conntac.hackernews.model.ItemType;
import com.conntac.hackernews.model.JobItem;
import com.conntac.hackernews.model.PollItem;
import com.conntac.hackernews.model.PolloptItem;
import com.conntac.hackernews.model.StoryItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class ApiHelper {
    /**
     * Method to get top stories from api
     * @param listener
     * @param errorListener
     * @return
     */
    public static GsonGetRequest<ArrayList<Long>> getDummyObjectArray( Response.Listener<ArrayList<Long>> listener,Response.ErrorListener errorListener)
    {
        final String url = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";
        final Gson gson = new GsonBuilder().create();
        return new GsonGetRequest<>( url,new TypeToken<ArrayList<Long>>() {}.getType(),gson,listener,errorListener);
    }
    public static GsonGetRequest<BaseItem> getBaseItem(Long id,Response.Listener<BaseItem> listener, Response.ErrorListener errorListener)
    {
        final String url = " https://hacker-news.firebaseio.com/v0/item/"+id+".json?print=pretty";
        RuntimeTypeAdapterFactory<BaseItem> typeAdapterFactory = RuntimeTypeAdapterFactory.of(BaseItem.class)
                .registerSubtype(StoryItem.class, ItemType.story.name())
                .registerSubtype(CommentItem.class,ItemType.comment.name())
                .registerSubtype(AskItem.class,ItemType.ask.name())
                .registerSubtype(PollItem.class,ItemType.poll.name())
                .registerSubtype(PolloptItem.class,ItemType.pollopt.name())
                .registerSubtype(JobItem.class,ItemType.job.name());
        final Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeAdapterFactory).create();
        return new GsonGetRequest<>( url,new TypeToken<BaseItem>() {}.getType(),gson,listener,errorListener);
    }
}
