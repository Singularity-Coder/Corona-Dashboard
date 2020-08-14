package com.singularitycoder.coronadashboard.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public final class AppUtils extends AppCompatActivity {

    @NonNull
    private final String TAG = "AppUtils";

    @Nullable
    private static AppUtils _instance;

    @NonNull
    public static synchronized AppUtils getInstance() {
        if (null == _instance) _instance = new AppUtils();
        return _instance;
    }

    public final boolean hasInternet(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    public final void setStatusBarColor(Activity activity, int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, statusBarColor));
            window.requestFeature(window.FEATURE_NO_TITLE);
            window.requestFeature(Window.FEATURE_PROGRESS);
            window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void epochToDate(long epochTime) {
        Date date = new Date(epochTime);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String formatted = format.format(date);
        System.out.println(formatted);
    }

    @SuppressLint("SimpleDateFormat")
    public final String getDateTime(Date date) {

        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // split date and time for event created date
        String[] arrOfStr = dateTime.split(" ", 2);
        ArrayList<String> dateAndTime = new ArrayList<>(Arrays.asList(arrOfStr));

        // convert date to dd/mm/yyyy
        Date dateObj = null;
        try {
            dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(dateAndTime.get(0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDate = new SimpleDateFormat("dd MMM yyyy").format(dateObj);
        Log.d(TAG, "date: " + outputDate);

        // convert time to 12 hr format
        Date timeObj = null;
        try {
            timeObj = new SimpleDateFormat("H:mm:ss").parse(dateAndTime.get(1));
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        String outputTime = new SimpleDateFormat("hh:mm a").format(timeObj);
        Log.d(TAG, "time: " + outputTime);

        return outputDate + " at " + outputTime;
    }

}