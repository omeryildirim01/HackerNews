package com.conntac.hackernews.model;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class PolloptItem extends  BaseItem {
    private String by;
    private Long poll;
    private int score;
    private String text;
    private Long time;

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Long getPoll() {
        return poll;
    }

    public void setPoll(Long poll) {
        this.poll = poll;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
