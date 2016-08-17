package com.xeeshi.nsoft;

import android.support.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xeeshi.nsoft.Objects.Data;
import com.xeeshi.nsoft.Objects.Job;
import com.xeeshi.nsoft.Objects.Map;
import com.xeeshi.nsoft.Objects.Progress;
import com.xeeshi.nsoft.Objects.Settings;
import com.xeeshi.nsoft.Objects.User;
import com.xeeshi.nsoft.Utils.Common;
import com.xeeshi.nsoft.Utils.Constants;
import com.xeeshi.nsoft.Utils.ListGsonSerializer;
import com.xeeshi.nsoft.Utils.ToStringConverter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ZEESHAN on 13/08/16.
 */
public class App extends MultiDexApplication {

    private static int TIME_OUT = 60 * 2;
    private OkHttpClient client;

    private Retrofit retrofitGSONBaseURL, retrofitStringBaseURL;

    private static App mAppInstance;

    public static synchronized App getmAppInstance() {
        return mAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;

        // registering font path with Calligraphy library

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                    setDefaultFontPath("fonts/PT-Sans-Bold.ttf").
                    setFontAttrId(R.attr.fontPath).
                    build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                    setDefaultFontPath("fonts/PT-Sans-Bold-Italic.ttf").
                    setFontAttrId(R.attr.fontPath).
                    build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                setDefaultFontPath("fonts/PT-Sans-Italic.ttf").
                setFontAttrId(R.attr.fontPath).
                build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                setDefaultFontPath("fonts/Roboto-Slab-Bold.ttf").
                setFontAttrId(R.attr.fontPath).
                build());


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                setDefaultFontPath("fonts/Roboto-Slab-Regular.ttf").
                setFontAttrId(R.attr.fontPath).
                build());


        // last registered font path will effect whole app if we don't specify font family in widget
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                setDefaultFontPath("fonts/PT-Sans-Regular.ttf").
                setFontAttrId(R.attr.fontPath).
                build());

        client = new OkHttpClient.Builder().
                connectTimeout(TIME_OUT, TimeUnit.SECONDS).
                readTimeout(TIME_OUT, TimeUnit.SECONDS).
                writeTimeout(TIME_OUT, TimeUnit.SECONDS).
                build();

        // to get json response into POJO and removing conflict with ORM active android lib in converter factory
        retrofitGSONBaseURL = new Retrofit.Builder().
                baseUrl(Constants.BASE_URL).
                client(getClient()).
                addConverterFactory(GsonConverterFactory.create(new GsonBuilder().
                    excludeFieldsWithoutExposeAnnotation().
                create())).
                build();

        // to get response into string or empty body with positive or negitive response code
        retrofitStringBaseURL = new Retrofit.Builder().
                baseUrl(Constants.BASE_URL).
                client(getClient()).
                addConverterFactory(new ToStringConverter()).
                build();


        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));


        Configuration.Builder activeAndroidConfig = new Configuration.Builder(this);
        activeAndroidConfig.addModelClasses(Data.class, User.class, Job.class, Progress.class, Map.class, Settings.class);
        activeAndroidConfig.addTypeSerializer(ListGsonSerializer.class);
        ActiveAndroid.initialize(activeAndroidConfig.create());


        Settings settings = Settings.getSingleItem();
        if (null == settings) {
            Settings settings1 = new Settings();
            settings1.setLocale("en");
            settings1.save();

            Common.SetLocale(this.getBaseContext(), null, "en", false);
        } else {
            if (null!=settings.getLocale() && settings.getLocale().length()>0)
                Common.SetLocale(this.getBaseContext(), null, settings.getLocale() , false);
            else
                Common.SetLocale(this.getBaseContext(), null, "en", false);
        }
    }


    public OkHttpClient getClient() {
        return client;
    }

    public Retrofit getRetrofitGSONBaseURL() {
        return retrofitGSONBaseURL;
    }

    public Retrofit getRetrofitStringBaseURL() {
        return retrofitStringBaseURL;
    }
}
