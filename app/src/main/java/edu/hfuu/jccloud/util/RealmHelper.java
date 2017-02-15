package edu.hfuu.jccloud.util;

import android.content.Context;

import edu.hfuu.jccloud.model.BarCode;
import io.realm.Realm;

/**
 * Created by codeest on 16/8/16.
 */

public class RealmHelper {

    private Realm mRealm;

    public RealmHelper(Context mContext) {
        mRealm = Realm.getInstance(mContext);
    }

    /**
     //     * 查询一个空闲BARCOCE
     //     */
    public String getFirstUnusedBarcod() {
        String tem="";
        BarCode item = mRealm.where(BarCode.class).equalTo("used",false).findFirst();
        if (item !=null) {
            tem=item.getCode();
            mRealm.copyFromRealm(item);
        }

        return tem;
    }

//    /**
//     * 添加
//     */
//    public void insertHistoryLike(T bean) {
//        mRealm.beginTransaction();
//        mRealm.copyToRealm(bean);
//        mRealm.commitTransaction();
//    }
//
//    /**
//     * 删除
//     */
//    public void deleteObjectLike(String eId) {
//        T bean = mRealm.where(T.class).equalTo("eId", eId).findFirst();
//        mRealm.beginTransaction();
//        bean.deleteFromRealm();
//        mRealm.commitTransaction();
//    }

//    /**
//     * 查询所有收
//     */
//    public List<HistoryLikeBean> queryAllHistoryLike() {
//        RealmResults<HistoryLikeBean> likeList = mRealm.where(HistoryLikeBean.class).findAll();
//        return mRealm.copyFromRealm(likeList);
//    }
//
//    /**
//     * 查询
//     */
//    public boolean queryHistoryLike(String eId) {
//        RealmResults<HistoryLikeBean> results = mRealm.where(HistoryLikeBean.class).equalTo("eId", eId).findAll();
//        if (results.size() == 0) {
//            return false;
//        } else {
//            return true;
//        }
//    }

}
