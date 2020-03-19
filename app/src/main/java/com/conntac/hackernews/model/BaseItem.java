package com.conntac.hackernews.model;

/**
 * Created by Omer YILDIRIM on 7/16/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class BaseItem  {
    private long id;
    private ItemType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
