package edu.hfuu.jccloud;

import android.content.DialogInterface;
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

import java.util.TreeMap;

import edu.hfuu.jccloud.constants.StringConsts;
import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.MyViewPage;
import edu.hfuu.jccloud.model.Project;
import edu.hfuu.jccloud.util.cacheHelper;
import edu.hfuu.jccloud.view.MainDbContentView;
import edu.hfuu.jccloud.view.MainDbSerilizeTest;
import edu.hfuu.jccloud.view.MainSummary;
import edu.hfuu.jccloud.view.SZ.SZ01_Dynamic;
import edu.hfuu.jccloud.view.SZ.SZ05_Static;
import io.realm.Realm;
import io.realm.RealmResults;

//import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity  {

    public static final String TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    TabLayout tabs;

    private int iPrjects = 2;
    Project myP0, myP1;
    TreeMap<String, MyViewPage> viewPageTreeMap0, viewPageTreeMap1;
    private cacheHelper projectId, menuGroupId;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private SublimeNavigationView mNavigationView;

    private int POP_UI = -1;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (SublimeNavigationView) findViewById(R.id.navigation_view);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        setSupportActionBar(toolbar);


        viewPageTreeMap0 = initViewPages0();
        viewPageTreeMap1 = initViewPages1();

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        projectId = new cacheHelper<>(-1, -1);
        menuGroupId = new cacheHelper<>(-1, -1);
        setSupportViewPages(viewPageTreeMap0);
        int curId = 0;
        projectId.cacahe(curId);

        //Creating The Navigation Menu bar
        setUpActionBar(mDrawerLayout);
        setUpMenuItemsRunTime();
        setUpNavigationView();

        //
//        clearBarcode();
        initBarcode();
    }



    public TreeMap<String, MyViewPage> initViewPages0() {
        TreeMap<String, MyViewPage> v0 = new TreeMap<>();
        MyViewPage myViewPage0 = new MyViewPage("0", MainSummary.class.getName(), "任务总览");
        v0.put("" + 0, myViewPage0);
        MyViewPage myViewPage1 = new MyViewPage("1", MainDbContentView.class.getName(), "测试页");
        v0.put("1", myViewPage1);
        MyViewPage myViewPage2 = new MyViewPage("2", MainDbSerilizeTest.class.getName(), "Realm测试页");
        v0.put("2", myViewPage2);
        return v0;
    }

    public TreeMap<String, MyViewPage> initViewPages1() {
        TreeMap<String, MyViewPage> v1 = new TreeMap<>();
        MyViewPage myViewPage0 = new MyViewPage("0", SZ05_Static.class.getName(), StringConsts.SR05A);
        v1.put("0", myViewPage0);
        MyViewPage myViewPage1 = new MyViewPage("1", SZ01_Dynamic.class.getName(), StringConsts.SR05B);
        v1.put("1", myViewPage1);
        /* "地下水采样现场记录A1", "地下水采样现场记录A2",
        "地表水现场采样记录表A1", "地表水现场采样记录表A2",
                "废水现场采样记录A1", "废水现场采样记录A2",
                "大气降水现场采样原始记录A1", "大气降水现场采样原始记录A2",
                "气体现场采样记录A1", "气体现场采样记录A2"};*/

        return v1;
    }

    public void initBarcode() {
        RealmResults<BarCode> barCodes = realm.where(BarCode.class).findAll();
//        Toast.makeText(getApplicationContext(), "0-Size[]:/" + barCodes.size(), Toast.LENGTH_SHORT).show();


        if (barCodes.size() < 5) {
//            for (int i = 0; i < 3; i++) {
//                realm.beginTransaction();
//                BarCode u = realm.createObject(BarCode.class);
//                u.setCode("2006-01-02-Used1111" + i);
//                u.setGroupId("地下水采样现场记录A2");
//                u.setUsed(true);
//                u.setSampleId(UUID.randomUUID().toString());
//                u.setGroupId("地下水采样现场记录A2");
//                realm.commitTransaction();
////                Toast.makeText(getApplicationContext(), "0-insert:" + "["+i+"]/"+u.getId() + ":" + u.getbCode(), Toast.LENGTH_SHORT).show();
//            }
            for (int i = 0; i < 5; i++) {
                realm.beginTransaction();
                BarCode u = realm.createObject(BarCode.class,"2007-01-02-UnusedBarcode00000" + i);
//                u.setCode();
                u.setGroupId("000");
                u.setUsed(false);
                u.setSampleId("000");
                u.setGroupId("000");
                realm.commitTransaction();
//                Toast.makeText(getApplicationContext(), "0-insert:" + "["+i+"]/"+u.getId() + ":" + u.getbCode(), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void clearBarcode() {
        final RealmResults<BarCode> results = realm.where(BarCode.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                // Delete all matches
                results.clear();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    private void setSupportViewPages(TreeMap<String, MyViewPage> mTitles) {
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.vpPager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (TabLayout) findViewById(R.id.sliding_tabs);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setupWithViewPager(pager);
//        if(mTitles.get())
    }


    /**
     * 初始化控件，初始化Action栏
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
    private void setUpNavigationView() {
        // set listener to get notified of menu events
        mNavigationView.setNavigationMenuEventListener(new OnNavigationMenuEventListener() {
            @Override
            public boolean onNavigationMenuEvent(OnNavigationMenuEventListener.Event event,
                                                 SublimeBaseMenuItem menuItem) {
                switch (event) {
                    case CHECKED:
                        Log.i(TAG, "Item checked");
//                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
//                                menuItem.getItemId()+" CHECKED",Toast.LENGTH_SHORT).show();
                        break;
                    case UNCHECKED:
                        Log.i(TAG, "Item unchecked");
//                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
//                                menuItem.getItemId()+" UNCHECKED",Toast.LENGTH_SHORT).show();
                        break;
                    case GROUP_EXPANDED:
                        Log.i(TAG, "Group expanded");
//                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
//                                menuItem.getItemId()+" GROUP_EXPANDED",Toast.LENGTH_SHORT).show();
                        break;
                    case GROUP_COLLAPSED:
                        Log.i(TAG, "Group collapsed");
//                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
//                                menuItem.getItemId()+" GROUP_COLLAPSED",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        //CLICK
                        // Something like handleClick(menuItem);
                        // Here, we toggle the 'checked' state
                        Log.i(TAG, menuItem.getItemId() + "@Item clicked");
//                        Toast.makeText(getApplicationContext(),menuItem.getGroupId()+"/"+
//                                menuItem.getItemId()+" CLICK",Toast.LENGTH_SHORT).show();
                        menuItem.setChecked(!menuItem.isChecked());
                        //Check to see which item was being clicked and perform appropriate action

                        break;
                }
                handleMenuItemClick(menuItem);
                return true;
            }

            public void handleMenuItemClick(SublimeBaseMenuItem menuItem) {
//                Toast.makeText(getApplicationContext(),
//                        "ItemId:" + menuItem.getItemId() +
//                                "/GroupId" + menuItem.getGroupId() +
//                                "/Title:" + menuItem.getTitle() +
//                                "/Hint:?" + findIdByTitle(menuItem.getTitle())
//                        , Toast.LENGTH_SHORT).show();

                int newClickId = menuItem.getItemId();
                menuGroupId.cacahe(newClickId);

                //group switch
                switch (menuItem.getGroupId()) {
                    case 1://group 1
                        if (0 != (int) projectId.getNowItem()) {//project 0 need redrawer!!!
                            //Project ID=0<-->MAP0<-->group 1
                            setSupportViewPages(viewPageTreeMap0);//summary
                            projectId.cacahe(0);
                        }
                        break;
                    case 2:
//                        Toast.makeText(getApplicationContext(),"Group1 Selected",Toast.LENGTH_SHORT).show();
                        int itemIndex = findIdByTitle(menuItem.getTitle());
                        if (itemIndex > POP_UI) {
                            //Group 内部
                            if (1 != (int) projectId.getNowItem()) {//project 1 need redrawer!!!
                                //Project ID=0<-->MAP0<-->group 1
                                setSupportViewPages(viewPageTreeMap1);//project1
                                projectId.cacahe(1);
                            }
                            pager.setCurrentItem(itemIndex);//select item, no refrsh table ui
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                                .setTitle("退出")
                                .setMessage("确认退出吗？")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .create()
                                .show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (!menuItem.getTitle().toString().startsWith("任务"))
                    mDrawerLayout.closeDrawers();
            }

            public int findIdByTitle(CharSequence t) {
                int index = -1;
                for (String id : viewPageTreeMap1.keySet()) {
                    if (viewPageTreeMap1.get(id).getTitle().startsWith(t.toString()))
                        index = Integer.parseInt(id);
                }
                return index;
            }
        });
    }


    /**
     * 初始化控件，初始化导航菜单
     */
    private void setUpMenuItemsRunTime() {

        //adding items run time
        final SublimeMenu menu = mNavigationView.getMenu();
        SublimeGroup firstGroup = menu.addGroup(false, false, true, true, SublimeGroup.CheckableBehavior.NONE);
        SublimeBaseMenuItem myProjectsMenu = menu.addTextItem(firstGroup.getGroupId(), "My Projects", getString(R.string.projects), true);
        myProjectsMenu.setCheckable(false);
        myProjectsMenu.setIcon(R.drawable.archive);

        //title of menu
        for (int i = 0; i < 2; i++) {
            SublimeGroup sublimeGroup = menu.addGroup(true, true, true, true, SublimeGroup.CheckableBehavior.SINGLE);
            menu.addGroupHeaderItem(sublimeGroup.getGroupId(), "任务" + i, "", false);

            for (MyViewPage page : viewPageTreeMap1.values()) {
                SublimeBaseMenuItem tempBaseMenu = menu.addCheckboxItem(sublimeGroup.getGroupId(), page.getTitle(), "", true);
                tempBaseMenu.setIcon(R.drawable.assignment);
                tempBaseMenu.setCheckable(true);
                tempBaseMenu.setChecked(false);
            }

            menu.addSeparatorItem(sublimeGroup.getGroupId()); //(doesn't work)
        }

        SublimeGroup lastGroup = menu.addGroup(false, false, true, true, SublimeGroup.CheckableBehavior.NONE);
        SublimeBaseMenuItem exitBaseMenu = menu.addTextItem(lastGroup.getGroupId(), "Exit", getString(R.string.exit), true);
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
