package com.github.jarvis.base;


import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Locale;

/**
 * This class is used as a base application class.
 * @author Shivendra
 */
public class BaseApplication extends Application {

    public static final String TAG = BaseApplication.class
            .getSimpleName();
    private Locale locale = null;

    private Activity mCurrentActivity = null;

    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            Locale.setDefault(locale);
            Configuration config = new Configuration(newConfig);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }
}
