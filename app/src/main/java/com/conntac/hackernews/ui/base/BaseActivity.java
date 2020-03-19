package com.conntac.hackernews.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static String TAG ="BaseActivity";
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public abstract void initView();
}
