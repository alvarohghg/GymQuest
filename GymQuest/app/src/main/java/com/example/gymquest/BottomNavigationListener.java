package com.example.gymquest;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationListener implements BottomNavigationView.OnItemSelectedListener {

    private Context context;

    public BottomNavigationListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_schedule:
                Intent mainActivityIntent = new Intent(context, MainActivity.class);
                context.startActivity(mainActivityIntent);
                return true;

//             todo once page is ready uncomment this
//            case R.id.menu_victory_hall:
//                Intent victoryHallIntent = new Intent(context, VictoryHall.class);
//                context.startActivity(victoryHallIntent);
//                return true;

            case R.id.menu_all_exercises:
                Intent allExercisesIntent = new Intent(context, AllExercises.class);
                context.startActivity(allExercisesIntent);
                return true;

            case R.id.menu_profile:
                Intent profileIntent = new Intent(context, UserProfile.class);
                context.startActivity(profileIntent);
                return true;

            default:
                return false;
        }
    }
}

