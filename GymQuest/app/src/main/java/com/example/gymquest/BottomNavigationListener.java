package com.example.gymquest;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationListener implements BottomNavigationView.OnItemSelectedListener {

    private static BottomNavigationListener instance;
    private Context context;
    private BottomNavigationView bottomNavigationView;

    public BottomNavigationListener(Context context, BottomNavigationView bottomNavigationView) {
        this.context = context;
        this.bottomNavigationView = bottomNavigationView;
    }

    public static synchronized BottomNavigationListener getInstance(Context context, BottomNavigationView bottomNavigationView) {
        if (instance == null) {
            instance = new BottomNavigationListener(context.getApplicationContext(), bottomNavigationView);
        }
        return instance;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Log.d("Clicked Menu", "Menu item clicked: " +itemId);
        switch (itemId) {
            case R.id.menu_schedule:
                customizeMenuColors(itemId);
                Intent planificationActivityIntent = new Intent(context, Planification.class);
                planificationActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(planificationActivityIntent);
                return true;

//            case R.id.menu_victory_hall:
//                Intent victoryHallIntent = new Intent(context, VictoryHall.class);
//                context.startActivity(victoryHallIntent);
//                return true;

            case R.id.menu_all_exercises:
                customizeMenuColors(itemId);
                Intent allExercisesIntent = new Intent(context, AllExercises.class);
                allExercisesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(allExercisesIntent);
                return true;

            case R.id.menu_profile:
                customizeMenuColors(itemId);
                Intent profileIntent = new Intent(context, UserProfile.class);
                profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(profileIntent);
                return true;

            default:
                return false;
        }
    }


    public void customizeMenuColors(int activeItemId) {
        int menuSize = bottomNavigationView.getMenu().size();

        for (int i = 0; i < menuSize; i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            boolean isActive = menuItem.getItemId() == activeItemId;

            // Set background color
            int backgroundResId = isActive ? R.color.bottom_navigation_item_background_selected : R.color.bottom_navigation_item_background;

            // Set icon color
            Drawable icon = menuItem.getIcon();
            if (icon != null) {
                int iconColor = isActive ? R.color.bottom_navigation_item_icon_selected : R.color.bottom_navigation_item_icon;
                icon.setTint(ContextCompat.getColor(context, iconColor));
            }

            // Set background drawable
            View view = bottomNavigationView.findViewById(menuItem.getItemId());
            view.setBackgroundResource(backgroundResId);
        }
    }



}
