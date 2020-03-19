package com.conntac.hackernews.model;

import java.util.List;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class CommentItem extends BaseItem {
    private String by;
    private List<Long> kids;
    private Long parent;
    private String text;
    private Long time;


    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public List<Long> getKids() {
        return kids;
    }

    public void setKids(List<Long> kids) {
        this.kids = kids;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


}
