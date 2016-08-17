package com.xeeshi.nsoft.Objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ZEESHAN on 17/08/16.
 */
@Table(name = "Settings")
public class Settings extends Model {

    @Column(name = "locale")
    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public static Settings getSingleItem() {
        return new Select().from(Settings.class).executeSingle();
    }

    public static List<Settings> getAll() {
        return new Select().from(Settings.class).execute();
    }

    public static void deleteAllRecords() {
        new Delete().from(Settings.class).execute();
    }

    @Override
    public String toString() {
        return "Settings{" +
                "locale='" + locale + '\'' +
                '}';
    }
}
