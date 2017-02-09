package edu.hfuu.jccloud.util;

/**
 * Created by mrlgb on 2017/2/9.
 */

public class cacheHelper{
    private int oldItem =-1;
    private int nowItem =-1;

    public void cacahe(int newItem){
        setOldItem(getNowItem());//new->old
        setNowItem(newItem);

    }

    public int getOldItem() {
        return oldItem;
    }

    public void setOldItem(int oldItem) {
        this.oldItem = oldItem;
    }

    public int getNowItem() {
        return nowItem;
    }

    public void setNowItem(int nowItem) {
        this.nowItem = nowItem;
    }
}
