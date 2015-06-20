package com.example.franzejr.sampleapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;
import com.parse.ParseInstallation;

import model.CurrentUser;
import model.User;
import ui.activities.LoginActivity;
import ui.activities.MainActivity;

public class MyApplication extends Application {

    public static final String PATH_TYPEFACE_FOLDER = "typefaces/";
    public static final String PATH_TYPEFACE_CUSTOM = PATH_TYPEFACE_FOLDER + "montserrat-regular-webfont.ttf";

    private static Typeface customTypeface;
    private SharedPreferences mPreferences;

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;
    private static MyApplication sInstance;


    /**
     * Log or request TAG
     */
    public static final String TAG = "SampleApplication";

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized MyApplication getInstance() {
        return sInstance;
    }


    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

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
        // initialize the singleton
        sInstance = this;
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
