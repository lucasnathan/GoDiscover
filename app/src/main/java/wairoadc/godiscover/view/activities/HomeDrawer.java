package wairoadc.godiscover.view.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.NavDrawerListAdapter;
import wairoadc.godiscover.view.models.NavDrawerItem;
import wairoadc.godiscover.utilities.IntentIntegrator;

/**
 * Created by Lucas on 23/01/2015.
 */
public class HomeDrawer extends Activity {
    public DrawerLayout drawerLayout;
    public ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;
    //QrCode Messages
    private String DEFAULT_MESSAGE;
    private String DEFAULT_YES;
    private String DEFAULT_NO;
    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    protected void onCreate(Bundle savedInstanceState)
    {
        // R.id.drawer_layout should be in every activity with exactly the same id.
        super.onCreate(savedInstanceState);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_home_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        navDrawerItems = new ArrayList<NavDrawerItem>();

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],navMenuIcons.getResourceId(0,-1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],navMenuIcons.getResourceId(5,-1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        drawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        drawerList.setAdapter(adapter);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view)
            {
                getActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle(mDrawerTitle);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //drawing the background color on the selected item of the drawer
        if(this.getClass().getSimpleName().equals("MainActivity"))
            drawerList.setItemChecked(0, true);
        if(this.getClass().getSimpleName().equals("ScanQRActivity"))
            drawerList.setItemChecked(1, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // toggle nav drawer on selecting action bar app icon/title
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        Intent intent;
        // Handle action bar actions click
        switch (item.getItemId()) {

            case R.id.action_settings:
                if (!this.getClass().getSimpleName().equals("SettingsActivity")){
                    intent = new Intent(this.getBaseContext(),SettingsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                return true;
            case R.id.action_refresh:
                if (!this.getClass().getSimpleName().equals("RefreshActivity")){
                    intent = new Intent(this.getBaseContext(),RefreshActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        Intent intent;
        switch (position) {
            case 0:
                if (!this.getClass().getSimpleName().equals("MainActivity")){
                    intent = new Intent(this.getBaseContext(),GalleryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else
                drawerLayout.closeDrawer(drawerList);
                break;
            case 1:
                if (!this.getClass().getSimpleName().equals("ScanQRActivity")){
                    //QrCode Messages
                    DEFAULT_MESSAGE = getString(R.string.qr_code_message_english);
                    DEFAULT_YES =getString(R.string.yes);
                    DEFAULT_NO = getString(R.string.no);
                    drawerLayout.closeDrawer(drawerList);
                    IntentIntegrator scanIntegrator = new IntentIntegrator(this,DEFAULT_MESSAGE, DEFAULT_YES, DEFAULT_NO);
                    scanIntegrator.initiateScan();
                }else
                drawerLayout.closeDrawer(drawerList);
                break;
            default:
                break;
        }
    }
}