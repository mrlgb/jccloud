package edu.hfuu.jccloud;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.hfuu.jccloud.tableUI.Main_Summary;
import edu.hfuu.jccloud.tableUI.SZ01_Dynamic;
import edu.hfuu.jccloud.tableUI.SZ01_Static;

/**
 * Created by lgb on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    boolean isSummary=true;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, Boolean isSummary,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.isSummary=isSummary;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0&& isSummary) // if the position is 0 we are returning the First tab
        {
            Main_Summary main_summary = new Main_Summary();
            return main_summary;
        }
        else   if(position ==0 && !isSummary)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            SZ01_Static sz01_static = new SZ01_Static();
            return sz01_static;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            SZ01_Dynamic sz01_dynamic = new SZ01_Dynamic();
            return sz01_dynamic;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
