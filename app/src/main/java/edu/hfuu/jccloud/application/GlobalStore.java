package edu.hfuu.jccloud.application;

import android.app.Application;

import java.util.HashMap;

/**
 * Created by mrlgb on 2016/12/27.
 */

public class GlobalStore extends Application {

    private HashMap<String, String>  tabIndicators;



    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        tabIndicators =  new HashMap<String, String>() {
            {
                put("0000", "main");
                put("0001", "jcview1");
            }
        };
        //初始化全局变量
    }
}
