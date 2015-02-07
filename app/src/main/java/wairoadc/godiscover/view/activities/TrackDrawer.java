package wairoadc.godiscover.view.activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.NavDrawerListAdapter;
import wairoadc.godiscover.controller.ScanController;
import wairoadc.godiscover.model.Track;

import wairoadc.godiscover.utilities.IntentResult;


import wairoadc.godiscover.view.fragments.GalleryFragment;

import wairoadc.godiscover.view.models.NavDrawerItem;
import wairoadc.godiscover.utilities.IntentIntegrator;

/**
 * Created by Lucas on 23/01/2015.
 */
public class TrackDrawer extends FragmentActivity { public DrawerLayout drawerLayout;
    public ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private String DEFAULT_MESSAGE;
    private String DEFAULT_YES;
    private String DEFAULT_NO;
    Track currentTrack;

    // nav drawer title
    private CharSequence mDrawerTitle;
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
        setCurrentTrack();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        mTitle = mDrawerTitle = getTitle();

        //Load QrCode Messages
        DEFAULT_MESSAGE = getString(R.string.qr_code_message_english);
        DEFAULT_YES =getString(R.string.yes);
        DEFAULT_NO = getString(R.string.no);

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        navDrawerItems = new ArrayList<NavDrawerItem>();
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Information
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Static Map
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        // StoryLine
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Gallery
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Progress
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // Scan
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

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
                // calling onPrepareOptionsMenu() to show action bar icons
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                //invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //drawing the background color on the selected item of the drawer
        if(this.getClass().getSimpleName().equals("MainActivity"))
            drawerList.setItemChecked(0, true);
        if(this.getClass().getSimpleName().equals("InformationActivity"))
            drawerList.setItemChecked(1, true);
        if(this.getClass().getSimpleName().equals("StaticMapActivity"))
            drawerList.setItemChecked(2, true);
        if(this.getClass().getSimpleName().equals("StoryActivity"))
            drawerList.setItemChecked(3, true);
        if(this.getClass().getSimpleName().equals("GalleryFragment"))
            drawerList.setItemChecked(4, true);
        if(this.getClass().getSimpleName().equals("ProgressActivity"))
            drawerList.setItemChecked(5, true);
        if(this.getClass().getSimpleName().equals("ScanQRActivity"))
            drawerList.setItemChecked(6, true);

       // Toast.makeText(this, "Toast: " +currentTrack.get_id(), Toast.LENGTH_SHORT).show();
    }

    private void setCurrentTrack() {
        Intent intent = getIntent();
        currentTrack = intent.getParcelableExtra(MainActivity.TRACK_EXTRA);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // toggle nav drawer on selecting action bar app icon/title
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {

            case R.id.action_settings:
                if (!this.getClass().getSimpleName().equals("SettingsActivity")){
                    Intent intent = new Intent(this.getBaseContext(),SettingsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                return true;

            case R.id.action_refresh:
                if (!this.getClass().getSimpleName().equals("RefreshActivity")){
                    Intent intent = new Intent(this,RefreshActivity.class);
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
    protected void onResume() {
        super.onResume();
        //drawing the background color on the selected item of the drawer
        if(this.getClass().getSimpleName().equals("MainActivity"))
            drawerList.setItemChecked(0, true);
        if(this.getClass().getSimpleName().equals("InformationActivity"))
            drawerList.setItemChecked(1, true);
        if(this.getClass().getSimpleName().equals("StaticMapActivity"))
            drawerList.setItemChecked(2, true);
        if(this.getClass().getSimpleName().equals("StoryActivity"))
            drawerList.setItemChecked(3, true);
        if(this.getClass().getSimpleName().equals("GalleryFragment"))
            drawerList.setItemChecked(4, true);
        if(this.getClass().getSimpleName().equals("ProgressActivity"))
            drawerList.setItemChecked(5, true);
        if(this.getClass().getSimpleName().equals("ScanQRActivity"))
            drawerList.setItemChecked(6, true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    /* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_refresh).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }*/
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
                    intent = new Intent(this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                break;
            case 1:
                if (!this.getClass().getSimpleName().equals("InformationActivity")){
                    intent = new Intent(this,InformationActivity.class);
                    if(null != currentTrack)
                        intent.putExtra(MainActivity.TRACK_EXTRA,currentTrack);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    drawerLayout.closeDrawer(drawerList);
                    startActivity(intent);
                }else
                    drawerLayout.closeDrawer(drawerList);
                break;
            case 2:
                if (!this.getClass().getSimpleName().equals("StaticMapActivity")){
                    intent = new Intent(this,StaticMapActivity.class);
                    if(null != currentTrack)
                        intent.putExtra(MainActivity.TRACK_EXTRA,currentTrack);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    drawerLayout.closeDrawer(drawerList);
                    startActivity(intent);
                }else
                    drawerLayout.closeDrawer(drawerList);
                break;
            case 3:
                if (!this.getClass().getSimpleName().equals("StoryActivity")){
                    intent = new Intent(this,StoryActivity.class);
                    if(null != currentTrack)
                        intent.putExtra(MainActivity.TRACK_EXTRA,currentTrack);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    drawerLayout.closeDrawer(drawerList);
                    startActivity(intent);
                }else
                    drawerLayout.closeDrawer(drawerList);
                break;
            case 4:
                if (!this.getClass().getSimpleName().equals("GalleryFragment")){
                    intent = new Intent(this,GalleryFragment.class);
                    if(null != currentTrack)
                        intent.putExtra(MainActivity.TRACK_EXTRA,currentTrack);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    drawerLayout.closeDrawer(drawerList);
                    startActivity(intent);
                }else
                    drawerLayout.closeDrawer(drawerList);
                break;
            case 5:
                if (!this.getClass().getSimpleName().equals("ProgressActivity")){
                    intent = new Intent(this,ProgressActivity.class);
                    if(null != currentTrack)
                        intent.putExtra(MainActivity.TRACK_EXTRA,currentTrack);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    drawerLayout.closeDrawer(drawerList);
                    startActivity(intent);
                }else
                    drawerLayout.closeDrawer(drawerList);
                break;
            case 6:
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            processScanResult(scanningResult.getContents());
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void processScanResult(String scanResultString) {
        String trackArray[] = scanResultString.split(",");
        ScanController scanController = new ScanController(this);
        int spotIndex = 0;
        try{
            spotIndex = Integer.parseInt(trackArray[1]);
        } catch (NumberFormatException e) {
            Toast.makeText(this,"Scan error",Toast.LENGTH_SHORT);
        }
        Track scanTrack = scanController.scanCode(trackArray[0],spotIndex);
        if(scanTrack == null) {
            Toast.makeText(this,"Scan Error: You don't have this track downloaded!",Toast.LENGTH_LONG).show();
        } else {
            currentTrack = scanTrack;
            Intent intent = new Intent(this,StoryActivity.class);
            if(null != currentTrack)
                intent.putExtra(MainActivity.TRACK_EXTRA,currentTrack);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            drawerLayout.closeDrawer(drawerList);
            startActivity(intent);
        }

    }

}