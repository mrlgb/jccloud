package edu.hfuu.jccloud;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.TreeMap;

import edu.hfuu.jccloud.model.MyViewPage;

/**
 * Created by lgb on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    TreeMap<String, MyViewPage> Titles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
//    boolean isSummary = true;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, TreeMap<String, MyViewPage> mTitles) {
        super(fm);
//        this.isSummary = isSummary;
//        for (String item : mTitles.keySet()) {
//            mTitles.get(item);
//        }

        this.Titles = mTitles;
        this.NumbOfTabs = mTitles.size();

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position){
        Fragment  base =new Fragment();
        String className="";
        className=Titles.get(""+position).getClassName();

        try {
            base = (Fragment) Class.forName(className).newInstance();
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
        return  base;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence temp = "";
        temp=Titles.get(""+position).getTitle();
        return temp;
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
