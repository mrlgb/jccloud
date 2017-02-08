package edu.hfuu.jccloud.util;

/**
 * Created by mrlgb on 2017/2/8.
 */

public class MakeObject {
    // Constructor, fields, initialization, etc...
    public Object MakeObject(Class<?> clazz) {
        Object o = null;

        try {
            o = Class.forName(clazz.getName()).newInstance();
        } catch (ClassNotFoundException e) {
            // There may be other exceptions to throw here,
            // but I'm writing this from memory.
            e.printStackTrace();
        }
        catch (java.lang.InstantiationException e) {
            // There may be other exceptions to throw here,
            // but I'm writing this from memory.
            e.printStackTrace();
        }
        catch (java.lang.IllegalAccessException e) {
            // There may be other exceptions to throw here,
            // but I'm writing this from memory.
            e.printStackTrace();
        }

        return o;
    }
}
