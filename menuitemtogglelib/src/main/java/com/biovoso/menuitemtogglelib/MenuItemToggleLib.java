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

package com.biovoso.menuitemtogglelib;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by mmanghnani on 27/03/15.
 */
public class MenuItemToggleLib extends RelativeLayout
{
    private View button_view;

    private ImageView item_1;
    private String item_1_desc;

    private ImageView item_2;
    private String item_2_desc;

    private MenuItemToggleListener menuItemToggleListener;
    private State state = State.ITEM_1;

    private AnimatorSet slide_up = new AnimatorSet();
    private AnimatorSet slide_down = new AnimatorSet();

    public static enum State
    {
        ITEM_1, ITEM_2
    }

    public interface MenuItemToggleListener
    {
        void onMITButtonClick(State state);
    }

    public MenuItemToggleLib(Context context)
    {
        super(context);
        init();
    }

    public MenuItemToggleLib(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.layout, this);

        button_view = findViewById(R.id.button_view);
        item_1 = (ImageView) findViewById(R.id.icon_1);
        item_2 = (ImageView) findViewById(R.id.icon_2);

        button_view.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                swapStates();
                menuItemToggleListener.onMITButtonClick(state);
            }
        });

        button_view.setOnLongClickListener(new OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                makeToast(state);
                return true;
            }
        });

        createAnimators();
    }

    private void createAnimators()
    {
        Resources r = getResources();
        float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());

        // Slide up;
        ObjectAnimator button_1_su_translateY = ObjectAnimator.ofFloat(item_1, "translationY", 0, -1f * height);
        button_1_su_translateY.setDuration(250);
        ObjectAnimator button_1_su_alpha = ObjectAnimator.ofFloat(item_1, "alpha", 1f, 0f);
        button_1_su_alpha.setDuration(200);
        button_1_su_alpha.setStartDelay(75);

        ObjectAnimator button_2_su_translateY = ObjectAnimator.ofFloat(item_2, "translationY", height, 0);
        button_2_su_translateY.setDuration(250);
        ObjectAnimator button_2_su_alpha = ObjectAnimator.ofFloat(item_2, "alpha", 0f, 1f);
        button_2_su_alpha.setDuration(125);
        button_2_su_alpha.setStartDelay(50);

        slide_up.playTogether(button_1_su_translateY, button_1_su_alpha, button_2_su_translateY, button_2_su_alpha);

        // Slide down
        ObjectAnimator button_1_sd_translateY = ObjectAnimator.ofFloat(item_1, "translationY", -1f * height, 0);
        button_1_sd_translateY.setDuration(250);
        ObjectAnimator button_1_sd_alpha = ObjectAnimator.ofFloat(item_1, "alpha", 0f, 1f);
        button_1_sd_alpha.setDuration(125);
        button_1_sd_alpha.setStartDelay(50);

        ObjectAnimator button_2_sd_translateY = ObjectAnimator.ofFloat(item_2, "translationY", 0,  height);
        button_2_sd_translateY.setDuration(250);
        ObjectAnimator button_2_sd_alpha = ObjectAnimator.ofFloat(item_2, "alpha", 1f, 0f);
        button_2_sd_alpha.setDuration(200);
        button_2_sd_alpha.setStartDelay(75);

        slide_down.playTogether(button_1_sd_translateY, button_1_sd_alpha, button_2_sd_translateY, button_2_sd_alpha);
    }

    private void makeToast(State state)
    {
        int screenWidth;
        WindowManager w = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            Point size = new Point();
            w.getDefaultDisplay().getSize(size);
            screenWidth = size.x;
        }
        else
        {
            Display d = w.getDefaultDisplay();
            screenWidth = d.getWidth();
        }

        int viewLocation[] = new int[2];
        getLocationOnScreen(viewLocation);

        String desc = state == State.ITEM_1 ? item_1_desc : item_2_desc;

        Toast toast = Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, screenWidth - viewLocation[0] - getWidth()/2, getHeight());
        toast.show();
    }

    public void initialiseComponent(MenuItemToggleListener listener, Drawable icon_1, String desc_1, Drawable icon_2, String desc_2)
    {
        this.menuItemToggleListener = listener;
        this.item_1.setImageDrawable(icon_1);
        this.item_2.setImageDrawable(icon_2);
        this.item_1_desc = desc_1;
        this.item_2_desc = desc_2;
    }

    public void setMenuItemToggleListener(MenuItemToggleListener listener)
    {
        this.menuItemToggleListener = listener;
    }

    public void setIcon_1(Drawable drawable)
    {
        item_1.setImageDrawable(drawable);
    }

    public void setIcon_2(Drawable drawable)
    {
        item_2.setImageDrawable(drawable);
    }

    public void setItem_1_desc(String desc)
    {
        item_1_desc = desc;
    }

    public void setItem_2_desc(String desc)
    {
        item_2_desc = desc;
    }

    public String getItem1_desc()
    {
        return item_1_desc;
    }

    public String getItem2_desc()
    {
        return item_2_desc;
    }

    public State getState()
    {
        return state;
    }

    public void swapStates()
    {
        switch (state)
        {
            case ITEM_1:
                state = State.ITEM_2;
                slide_up.start();
                break;
            case ITEM_2:
                state = State.ITEM_1;
                slide_down.start();
                break;
        }
    }
}
