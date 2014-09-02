package com.example.franzejr.sampleapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.parse.Parse;
import com.parse.ParseInstallation;

import model.CurrentUser;
import model.User;
import ui.LoginActivity;
import ui.MainActivity;

public class MyApplication extends Application {
    public static final String PATH_TYPEFACE_FOLDER = "typefaces/";
    public static final String PATH_TYPEFACE_CUSTOM = PATH_TYPEFACE_FOLDER + "montserrat-regular-webfont.ttf";

    private static Typeface customTypeface;
    private SharedPreferences mPreferences;

    public static void updateParseInstallation(User user) {
        ParseInstallation parseInstallation = ParseInstallation.getCurrentInstallation();
        parseInstallation.put("userId", user.getId());
        parseInstallation.saveInBackground();
    }


    public static CurrentUser currentUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("CurrentFan", context.MODE_PRIVATE);
        CurrentUser user = new CurrentUser();
        user.setId(preferences.getInt("id", -1));
        user.setEmail(preferences.getString("AuthEmail", ""));
        user.setName(preferences.getString("Name", ""));
        user.setAuthentication_token(preferences.getString("AuthToken", ""));
        user.setuID(preferences.getString("facebook_id", ""));
        user.setoAuthToken(preferences.getString("oAuthToken", null));

        return user;
    }

    public static void handleUnauthenticated(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public Typeface getCustomTypeface() {
        if (customTypeface == null)
            customTypeface = Typeface.createFromAsset(getAssets(), PATH_TYPEFACE_CUSTOM);


        return customTypeface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferences = getSharedPreferences("CurrentFan", MODE_PRIVATE);
        Parse.initialize(this, "", "");
    }

    public static void onSessionCreated(Context context, CurrentUser currentUser, boolean startFeedActivity) {
        SharedPreferences preferences = context.getSharedPreferences("CurrentFan", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("AuthToken", currentUser.getAuthentication_token());
        editor.putString("AuthEmail", currentUser.getEmail());
        editor.putString("Name", currentUser.getName());
        editor.putInt("id", currentUser.getId());
        editor.putString("facebook_id", currentUser.getuID());
        editor.putString("oAuthToken", currentUser.getoAuthToken());

        editor.apply();
        MyApplication.updateParseInstallation(currentUser);
        // launch the HomeActivity and close this one
        if (startFeedActivity) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }

    }

    public static void loadPreferences(Context context) {

    }
}
