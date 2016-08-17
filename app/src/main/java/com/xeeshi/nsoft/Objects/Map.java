package com.xeeshi.nsoft.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ZEESHAN on 13/08/16.
 */
@Table(name = "StaffLocations")
public class Map extends Model implements Parcelable {

    @Column(name = "long")
    @SerializedName("long")
    @Expose
    private String _long;

    @Column(name = "lat")
    @SerializedName("lat")
    @Expose
    private String lat;

    public Map() {
        super();
    }

    /**
     *
     * @return
     * The _long
     */
    public String getLong() {
        return _long;
    }

    /**
     *
     * @param _long
     * The long
     */
    public void setLong(String _long) {
        this._long = _long;
    }

    /**
     *
     * @return
     * The lat
     */
    public String getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     * The lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }



    protected Map(Parcel in) {
        _long = in.readString();
        lat = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_long);
        dest.writeString(lat);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Map> CREATOR = new Parcelable.Creator<Map>() {
        @Override
        public Map createFromParcel(Parcel in) {
            return new Map(in);
        }

        @Override
        public Map[] newArray(int size) {
            return new Map[size];
        }
    };




    @Override
    public String toString() {
        return "Map{" +
                "_long='" + _long + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}