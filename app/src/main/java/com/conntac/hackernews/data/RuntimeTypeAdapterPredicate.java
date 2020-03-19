package com.conntac.hackernews.data;
import com.google.gson.JsonElement;
/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */


public abstract class RuntimeTypeAdapterPredicate {

    public abstract String process(JsonElement element);

}
