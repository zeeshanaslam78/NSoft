package com.xeeshi.nsoft;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xeeshi.nsoft.Objects.Settings;
import com.xeeshi.nsoft.Objects.User;
import com.xeeshi.nsoft.Utils.Common;
import com.xeeshi.nsoft.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ZEESHAN on 14/08/16.
 */
public class DescriptionActivity extends Activity implements View.OnClickListener {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Common.setSelectedThemeForDescriptoin(DescriptionActivity.this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_description);

        TextView ad_txt_name, ad_txt_description, ad_txt_send_email, ad_txt_set_notificaiton, ad_txt_show_map;
        ImageView ad_img_close = (ImageView) findViewById(R.id.ad_img_close);

        ad_txt_name = (TextView) findViewById(R.id.ad_txt_name);
        ad_txt_description = (TextView) findViewById(R.id.ad_txt_description);
        ad_txt_send_email = (TextView) findViewById(R.id.ad_txt_send_email);
        ad_txt_set_notificaiton = (TextView) findViewById(R.id.ad_txt_set_notificaiton);
        ad_txt_show_map = (TextView) findViewById(R.id.ad_txt_show_map);

        ad_txt_description.setMovementMethod(new ScrollingMovementMethod());


        ad_img_close.setOnClickListener(this);
        ad_txt_send_email.setOnClickListener(this);
        ad_txt_set_notificaiton.setOnClickListener(this);
        ad_txt_show_map.setOnClickListener(this);


        user = getIntent().getParcelableExtra(Constants.USER);
        if (null!=user) {
            ad_txt_name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ad_txt_description.setText(Html.fromHtml(user.getDescription(), Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV));
            } else {
                ad_txt_description.setText(Html.fromHtml(user.getDescription()));
            }
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ad_img_close) {
            finish();
            overridePendingTransition(0, R.anim.slide_out_up);
        } else if (view.getId() == R.id.ad_txt_send_email) {

            if (null!=user) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode((null != user.getEmail() && user.getEmail().length() > 0) ? user.getEmail() : "") +
                        "?subject=" + Uri.encode("Subject") +
                        "&body=" + Uri.encode("Message");
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send Email"));
            }

        } else if (view.getId() == R.id.ad_txt_set_notificaiton) {

            if (null!=user) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));
                Date d = new Date(Long.parseLong(user.getDate()) * 1000L);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DescriptionActivity.this);
                mBuilder.setContentTitle(String.format("%s %s", user.getFirstName(), user.getLastName()));
                mBuilder.setContentText("Birthday notification scheduled at " + dateFormat.format(d));

                mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(String.format("%s %s", user.getFirstName(), user.getLastName())));
                mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Birthday notification scheduled at " + dateFormat.format(d)));

                mBuilder.setSmallIcon(R.drawable.ic_stat_name);

                mBuilder.setAutoCancel(true);
                mBuilder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));

                Notification notification = mBuilder.build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                long time = new Date().getTime();
                String tmpStr = String.valueOf(time);
                String last4Str = tmpStr.substring(tmpStr.length() - 5);
                int notificationId = Integer.valueOf(last4Str);

                notificationManager.notify(notificationId, notification);
                finish();
                overridePendingTransition(0, R.anim.slide_out_up);
            }


        } else if (view.getId() == R.id.ad_txt_show_map) {
            Intent mapIntent = new Intent(DescriptionActivity.this, MapsActivity.class);
            mapIntent.putExtra(Constants.USER, user);
            startActivity(mapIntent);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_out_up);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Settings settings = Settings.getSingleItem();
        if (null!=settings ) {
            if (null!=settings.getLocale() && settings.getLocale().length()>0)
                Common.SetLocale(DescriptionActivity.this, null, settings.getLocale() , false);
            else
                Common.SetLocale(DescriptionActivity.this, null, "English", false);
        } else
            Common.SetLocale(DescriptionActivity.this, null, "English", false);
    }


}
