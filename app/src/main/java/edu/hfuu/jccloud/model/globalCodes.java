package edu.hfuu.jccloud.model;

import java.util.HashMap;

/**
 * Created by mrlgb on 2017/2/6.
 */

public class globalCodes {
    public  int NUMBER=5;
    public  HashMap<String,BinaryCode> sCache;

    public int getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(int NUMBER) {
        this.NUMBER = NUMBER;
    }

    public HashMap<String, BinaryCode> getsCache() {
        return sCache;
    }

    public void setsCache(HashMap<String, BinaryCode> sCache) {
        this.sCache = sCache;
    }

    public static String[] getPlants() {
        return plants;
    }

    public static void setPlants(String[] plants) {
        globalCodes.plants = plants;
    }

    public globalCodes() {
        sCache =new HashMap<String,BinaryCode>();
        for (int i=0;i<NUMBER;i++){
            String key="201711111"+i;
            String Id=""+i;
            BinaryCode bc= new BinaryCode(key,true,Id);
            sCache.put(key,bc);
        }
        for (int i=0;i<NUMBER;i++){
            String key="20160000000000"+i;
            String Id="-1";
            BinaryCode bc= new BinaryCode(key,false,Id);
            sCache.put(key,bc);
        }

    }

    public static String[] plants = new String[]{
            "201702111110000",
            "201702111110001",
            "201702111110002",
            "201702111110003",
            "201702111110004",
            "201602000000000",
            "201602000000001",
            "201602000000002",
            "201602000000003",
            "201602000000004"
    };

}
