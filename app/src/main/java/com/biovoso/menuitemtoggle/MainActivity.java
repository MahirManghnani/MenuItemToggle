package com.biovoso.menuitemtoggle;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.biovoso.menuitemtogglelib.MenuItemToggleLib;

/**
 * Created by mmanghnani on 27/03/15.
 */
public class MainActivity extends ActionBarActivity implements MenuItemToggleLib.MenuItemToggleListener
{
    private MenuItemToggleLib menuItemToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_layout);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        menuItemToggle = (MenuItemToggleLib) menu.findItem(R.id.menuItemToggle).getActionView();
        menuItemToggle.initialiseComponent(this,
                getResources().getDrawable(R.drawable.ic_search_white_24dp), "Search",
                getResources().getDrawable(R.drawable.ic_done_white_24dp), "Done");
        return true;
    }

    @Override
    public void onMITButtonClick(MenuItemToggleLib.State state)
    {
        Log.d("MenuItemToggle", state.toString());
    }
}