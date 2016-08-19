package com.xeeshi.nsoft.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.xeeshi.nsoft.MainActivity;
import com.xeeshi.nsoft.Objects.Settings;
import com.xeeshi.nsoft.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Locale;

/**
 * Created by ZEESHAN on 16/08/16.
 */
public class Common {

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static int convertPaddingToPixel(int marginPadding, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (marginPadding * scale + 0.5f);
    }

    public static void giveFocusNShowKeyboard(Context context, View v) {
        if (v.requestFocus()) {
            InputMethodManager mgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    public static void SetLocale(Context context, Activity activity, String localeStr, boolean isRestartActivity) {

        if (localeStr.equals("BHS"))
            localeStr = "bs";
        else if (localeStr.equals("English"))
            localeStr = "en";


        Locale locale = new Locale(localeStr);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.
                getResources().
                updateConfiguration(config,
                        context.getResources().getDisplayMetrics());

        if (isRestartActivity) {
            activity.finish();
            Intent refresh = new Intent(activity, MainActivity.class);
            activity.startActivity(refresh);
        }
    }


    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trimSharedPrefs(Context context) {
        try {
            File dir = new File("/data/data/com.xeeshi.nsoft/shared_prefs");
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trimCodeCache(Context context) {
        try {
            File dir = new File("/data/data/com.xeeshi.nsoft/code_cache");
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void trimFilesFolder(Context context) {
        try {
            File dir = new File("/data/data/com.xeeshi.nsoft/files");
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trimLibFolder(Context context) {
        try {
            File dir = new File("/data/data/com.xeeshi.nsoft/lib");
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }


    public static void setSelectedTheme(Context context) {

        int themeStyleId = R.style.Blue_Theme_NoActionBar;

        Settings settings = Settings.getSingleItem();
        if (null!=settings) {
            if (null!=settings.getTheme() && settings.getTheme().length()>0) {
                if (settings.getTheme().equals("Purple"))
                    themeStyleId = R.style.Purple_Theme_NoActionBar;
            }
        }
        context.setTheme(themeStyleId);

        setSelectedFontSize(context, settings);
    }


    public static void setSelectedThemeForDescriptoin(Context context) {

        int themeStyleId = R.style.TransparentActivity_Blue;

        Settings settings = Settings.getSingleItem();
        if (null!=settings) {
            if (null!=settings.getTheme() && settings.getTheme().length()>0) {
                if (settings.getTheme().equals("Purple"))
                    themeStyleId = R.style.TransparentActivity_Purple;
            }
        }
        context.setTheme(themeStyleId);

        setSelectedFontSize(context, settings);
    }


    public static void setSelectedFontSize(Context context, Settings settings) {

        int fontStyleId = R.style.FontSize_Medium;

        if (null!=settings) {
            if (null!=settings.getFontSize() && settings.getFontSize().length()>0) {
                if (settings.getFontSize().equals("Small"))
                    fontStyleId = R.style.FontSize_Small;
                else if (settings.getFontSize().equals("Large"))
                    fontStyleId = R.style.FontSize_Large;
            }
        }

        // Set the theme for the activity.
        context.getTheme().applyStyle(fontStyleId, true);
    }



    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkinfo != null && networkinfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }


}
