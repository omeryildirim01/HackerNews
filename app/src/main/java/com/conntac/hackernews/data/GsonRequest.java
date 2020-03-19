package com.conntac.hackernews.data;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
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
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson gson;
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;

    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers,  Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        RuntimeTypeAdapterFactory<BaseItem> typeAdapterFactory = RuntimeTypeAdapterFactory.of(BaseItem.class)
                .registerSubtype(StoryItem.class, ItemType.story.name())
                .registerSubtype(CommentItem.class,ItemType.comment.name())
                .registerSubtype(AskItem.class,ItemType.ask.name())
                .registerSubtype(PollItem.class,ItemType.poll.name())
                .registerSubtype(PolloptItem.class,ItemType.pollopt.name())
                .registerSubtype(JobItem.class,ItemType.job.name());
        gson= new GsonBuilder().registerTypeAdapterFactory(typeAdapterFactory).create();
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(  response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(   gson.fromJson(json, clazz),HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}