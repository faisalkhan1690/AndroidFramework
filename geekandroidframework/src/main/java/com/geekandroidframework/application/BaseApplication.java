package com.geekandroidframework.application;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.geekandroidframework.BuildConfig;

import java.util.Timer;
import java.util.TimerTask;


public abstract class BaseApplication extends Application {

    private static final String LOG_TAG = "BaseApplication";
    public static boolean IS_LOG_ENABLE=false;
    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    public boolean mAppInBackground;
    private final static long MAX_ACTIVITY_TRANSITION_TIME_MS = 2000;


    @Override
    public void onCreate() {
        super.onCreate();
        this.initialize();
        if (BuildConfig.DEBUG) {
            Log.i(LOG_TAG, "onCreate()");
        }
    }

    public boolean isAppInBackground() {
        return mAppInBackground;
    }

    public void setAppInBackground(boolean isAppInBackground) {
        mAppInBackground = isAppInBackground;
    }


    public void onActivityResumed() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
        }

        if (this.mActivityTransitionTimer != null) {
            this.mActivityTransitionTimer.cancel();
        }
        setAppInBackground(false);
    }

    public void onActivityPaused() {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                setAppInBackground(true);
                if (BuildConfig.DEBUG) {
                    Log.i(LOG_TAG, "None of our activity is in foreground.");
                }
            }
        };

        this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask, MAX_ACTIVITY_TRANSITION_TIME_MS);
    }


    public ApplicationInfo getApplicationMetaData()
            throws NameNotFoundException {
        ApplicationInfo ai = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        return ai;
    }

    protected abstract void initialize();
}