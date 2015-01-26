package wairoadc.godiscover.view.activities;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import wairoadc.godiscover.R;
import wairoadc.godiscover.adapter.NavDrawerListAdapter;
import wairoadc.godiscover.model.NavDrawerItem;

/**
 * Created by Lucas on 23/01/2015.
 */
public class TrackDrawer extends Activity {
    public DrawerLayout drawerLayout;
    public ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

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
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],navMenuIcons.getResourceId(6,-1)));

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
                return true;
            case R.id.action_refresh:
                Intent intent = new Intent(TrackDrawer.this,RefreshActivity.class);
                startActivity(intent);
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
    /* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                intent = new Intent(TrackDrawer.this,MainActivity.class);
                startActivity(intent);
                TrackDrawer.this.finish();
                break;
            case 1:
                intent = new Intent(TrackDrawer.this,InfoActivity.class);
                startActivity(intent);
                TrackDrawer.this.finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            drawerLayout.closeDrawer(drawerList);
            //Log.e("Fragment",fragment.toString());
            //Toast.makeText(this.getApplicationContext(),fragment.get,Toast.LENGTH_LONG).show();
        } else {
            // error in creating fragment
            Log.e("HomeDrawer", "Error in creating fragment");
        }
    }
}