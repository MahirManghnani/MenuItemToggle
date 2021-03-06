/*
 * Copyright 2015 Mahir Manghnani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.biovoso.menuitemtoggle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.biovoso.menuitemtogglelib.MenuItemToggleLib;

/**
 * Created by mmanghnani on 27/03/15.
 */
public class MainActivity extends ActionBarActivity implements MenuItemToggleLib.MenuItemToggleListener
{
    private MenuItemToggleLib menuItemToggle;
    private TextView stateText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_layout);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stateText = (TextView) findViewById(R.id.stateText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        menuItemToggle = (MenuItemToggleLib) menu.findItem(R.id.menuItemToggle).getActionView();
        menuItemToggle.initialiseComponent(this,
                getResources().getDrawable(R.drawable.ic_search_white_24dp), "Search",
                getResources().getDrawable(R.drawable.ic_done_white_24dp), "Done");
        stateText.setText(menuItemToggle.getState().toString());
        return true;
    }

    @Override
    public void onMITButtonClick(MenuItemToggleLib.State state)
    {
        Log.d("MenuItemToggle", state.toString());
        stateText.setText(state.toString());
    }

    public void onCardClick(View view)
    {
        Uri uri = Uri.parse("https://github.com/MahirManghnani/MenuItemToggle");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}