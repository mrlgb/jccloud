package edu.hfuu.jccloud.util;

/**
 * Created by mrlgb on 2017/2/9.
 */

public class cacheHelper<T> {
    private T oldItem;
    private T  nowItem;
    private int cacheTimes=0;

    public cacheHelper( T oldItem, T nowItem) {
        this.oldItem = oldItem;
        this.nowItem = nowItem;
    }

    public void cacahe(T newItem){
        setOldItem(getNowItem());//new->old
        setNowItem(newItem);
        cacheTimes++;
    }

    public int getCacheTimes() {
        return cacheTimes;
    }

    public void setCacheTimes(int cacheTimes) {
        this.cacheTimes = cacheTimes;
    }

    public T getOldItem() {
        return oldItem;
    }

    public void setOldItem(T oldItem) {
        this.oldItem = oldItem;
    }

    public T getNowItem() {
        return nowItem;
    }

    public void setNowItem(T nowItem) {
        this.nowItem = nowItem;
    }
}
