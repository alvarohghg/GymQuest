package com.example.gymquest;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationListener implements BottomNavigationView.OnItemSelectedListener {

    private Context context;
    private BottomNavigationView bottomNavigationView;

    public BottomNavigationListener(Context context, BottomNavigationView bottomNavigationView) {
        this.context = context;
        this.bottomNavigationView = bottomNavigationView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Log.d("Clicked Menu", "Menu item clicked: " + itemId);
        switch (itemId) {
            case R.id.menu_schedule:
                Intent planificationActivityIntent = new Intent(context, Planification.class);
                planificationActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(planificationActivityIntent);
                return false;

            case R.id.menu_all_exercises:
                Intent allExercisesIntent = new Intent(context, AllExercises.class);
                allExercisesIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(allExercisesIntent);
                return true;

            case R.id.menu_profile:
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

        // Set icon color
        for (int i = 0; i < menuSize; i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            boolean isActive = menuItem.getItemId() == activeItemId;

            View view = bottomNavigationView.findViewById(menuItem.getItemId());

            Drawable iconDrawable = menuItem.getIcon();

            int iconColorResId = isActive ? R.color.bottom_navigation_item_icon_selected : R.color.bottom_navigation_item_icon;
            int iconColor = ContextCompat.getColor(context, iconColorResId);

            iconDrawable.setColorFilter(iconColor, PorterDuff.Mode.SRC_IN);
            menuItem.setIcon(iconDrawable);

            // Set the updated view back to the menu item
            menuItem.setActionView(view);
        }

        // Set bg color
        for (int i = 0; i < menuSize; i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            boolean isActive = menuItem.getItemId() == activeItemId;

            View view = bottomNavigationView.findViewById(menuItem.getItemId());

            int backgroundResId = isActive ? R.color.bottom_navigation_item_background_selected : R.color.bottom_navigation_item_background;
            view.setBackgroundResource(backgroundResId);
        }
    }

}
