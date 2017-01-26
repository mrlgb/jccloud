package edu.hfuu.jccloud;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.appeaser.sublimenavigationviewlibrary.OnNavigationMenuEventListener;
import com.appeaser.sublimenavigationviewlibrary.SublimeBaseMenuItem;
import com.appeaser.sublimenavigationviewlibrary.SublimeGroup;
import com.appeaser.sublimenavigationviewlibrary.SublimeMenu;
import com.appeaser.sublimenavigationviewlibrary.SublimeNavigationView;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    TabLayout tabs;
    CharSequence TitlesDefault[] = {"任务总览"};
    CharSequence Titles[] = {
            "地表水现场采样记录表A1", "地表水现场采样记录表A1",
            "地下水采样现场记录A1", "地下水采样现场记录A2",
            "废水现场采样记录A1", "废水现场采样记录A2",
            "大气降水现场采样原始记录A1", "大气降水现场采样原始记录A2",
            "气体现场采样记录A1", "气体现场采样记录A2"};
    int Numboftabs = 2;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private SublimeNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (SublimeNavigationView) findViewById(R.id.navigation_view);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        setUpViewPages(true, TitlesDefault, 1);

        //Creating The Navigation Menu bar
        setUpActionBar(mDrawerLayout);
        setUpMenuItemsRunTime();
                setUpNaviView();

        // Initial fab
//        setUpFab();
    }


    private void setUpViewPages(Boolean isSummary, CharSequence mTitles[], int mNumbOfTabsumb) {
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), isSummary, mTitles, mNumbOfTabsumb);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.vpPager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (TabLayout) findViewById(R.id.sliding_tabs);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setupWithViewPager(pager);
    }


    /**
     * 初始化控件，初始化导航栏
     */
    private void setUpActionBar(DrawerLayout mDrawerLayout) {
        ActionBar actionBar = getSupportActionBar();

        try {
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setSubtitle(getString(R.string.subtitle));
            actionBar.setDisplayShowTitleEnabled(true);
        } catch (Exception ignored) {
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle(getString(R.string.drawer_opened));
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    /**
     * 初始化控件，初始化导航栏
     */
    private void setUpNaviView() {
        // set listener to get notified of menu events
        mNavigationView.setNavigationMenuEventListener(new OnNavigationMenuEventListener() {
            @Override
            public boolean onNavigationMenuEvent(OnNavigationMenuEventListener.Event event,
                                                 SublimeBaseMenuItem menuItem) {
                switch (event) {
                    case CHECKED:
                        Log.i(TAG, "Item checked");
                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
                                menuItem.getItemId()+" CHECKED",Toast.LENGTH_SHORT).show();
                        break;
                    case UNCHECKED:
                        Log.i(TAG, "Item unchecked");
                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
                                menuItem.getItemId()+" UNCHECKED",Toast.LENGTH_SHORT).show();
                        break;
                    case GROUP_EXPANDED:
                        Log.i(TAG, "Group expanded");
                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
                                menuItem.getItemId()+" GROUP_EXPANDED",Toast.LENGTH_SHORT).show();
                        break;
                    case GROUP_COLLAPSED:
                        Log.i(TAG, "Group collapsed");
                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
                                menuItem.getItemId()+" GROUP_COLLAPSED",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        //CLICK
                        // Something like handleClick(menuItem);
                        // Here, we toggle the 'checked' state
                        Log.i(TAG, menuItem.getItemId() + "@Item clicked");
                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
                                menuItem.getItemId()+" CLICK",Toast.LENGTH_SHORT).show();
                        menuItem.setChecked(!menuItem.isChecked());
                        //Check to see which item was being clicked and perform appropriate action
                        break;
                }
                handleMenuItemClick(menuItem);
                return true;
            }

            public void handleMenuItemClick(SublimeBaseMenuItem menuItem) {

//                Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
//                        menuItem.getItemId()+" Selected",Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
//                switch (menuItem.getItemId()) {
//                    case R.id.item_Exit:
////                        Toast.makeText(getApplicationContext(),"Exit Selected",Toast.LENGTH_SHORT).show();
//                        new AlertDialog.Builder(MainActivity.this)
//                                .setTitle("Title")
//                                .setMessage("This is message")
//                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                    }
//                                })
//                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                    }
//                                })
//                                .create()
//                                .show();
//                        break;
//                    case R.id.item_Myprojects:
////                        Toast.makeText(getApplicationContext(),"My projects Selected",Toast.LENGTH_SHORT).show();
//                        setUpViewPages(true, TitlesDefault, 1);
//                        break;
//                    case R.id.item_progress:
////                        Toast.makeText(getApplicationContext(),"Progress Selected",Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.checkbox_item_Save:
//                        Toast.makeText(getApplicationContext(), "Upload Selected", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.group_header_1:
////                        Toast.makeText(getApplicationContext(),"Group1 Selected",Toast.LENGTH_SHORT).show();
//                        setUpViewPages(false, Titles, 2);
//                        break;
//                    case R.id.switch_item_11:
////                        Toast.makeText(getApplicationContext(),"Group1 Selected",Toast.LENGTH_SHORT).show();
//                        pager.setCurrentItem(0);
//                        break;
//                    case R.id.switch_item_12:
////                        Toast.makeText(getApplicationContext(),"Group1 Selected",Toast.LENGTH_SHORT).show();
//                        pager.setCurrentItem(1);
//                        break;
//                    default:
//                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
//                        break;
//                }

            }
        });
    }

    private void setUpMenuItemsRunTime() {

        //initdata
//        Project myProject = new Project("0001", "My Projects List", "List my all samples1");

        //adding items run time
        final SublimeMenu menu = mNavigationView.getMenu();

        //title of menu
        for(int i=0;i<2;i++) {
            SublimeGroup sublimeGroup = menu.addGroup(true, true, true, true, SublimeGroup.CheckableBehavior.SINGLE);
            menu.addGroupHeaderItem(sublimeGroup.getGroupId(), "任务"+i , "", false);

            for(int j=0;j<Titles.length;j++) {
//            menu.addTextItem(sublimeGroup.getGroupId(), Titles[i], "", true);
//                SublimeBaseMenuItem tempBaseMenu = menu.addSwitchItem(sublimeGroup.getGroupId(),Titles[i], "",true);
                SublimeBaseMenuItem tempBaseMenu = menu.addCheckboxItem(sublimeGroup.getGroupId(),Titles[i], "",true);
                tempBaseMenu.setIcon(R.drawable.assignment);
                tempBaseMenu.setCheckable(true);
                tempBaseMenu.setChecked(false);
            }

            menu.addSeparatorItem(sublimeGroup.getGroupId()); //(doesn't work)
        }

        SublimeGroup lastGroup = menu.addGroup(false, false, true, true, SublimeGroup.CheckableBehavior.NONE);
        SublimeBaseMenuItem exitBaseMenu = menu.addTextItem(lastGroup.getGroupId(),"Exit" , getString(R.string.exit), true);
        exitBaseMenu.setCheckable(false);
        exitBaseMenu.setIcon(R.drawable.exit);

        menu.finalizeUpdates();

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawer(mNavigationView);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);

//        if (searchItem != null) {
//            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//
//            // use this method for search process
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    // use this method when query submitted
//                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    // use this method for auto complete search process
//                    return false;
//                }
//            });
//
//        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                mDrawerLayout.closeDrawer(mNavigationView);
            } else {
                mDrawerLayout.openDrawer(mNavigationView);
            }
            return true;
        }

        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
