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
@Table(name = "StaffProgresss")
public class Progress extends Model implements Parcelable {

    @Column(name = "value")
    @SerializedName("value")
    @Expose
    private Integer value;

    @Column(name = "color")
    @SerializedName("color")
    @Expose
    private String color;

    public Progress() {
        super();
    }

    /**
     *
     * @return
     * The value
     */
    public Integer getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     *
     * @return
     * The color
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     * The color
     */
    public void setColor(String color) {
        this.color = color;
    }



    protected Progress(Parcel in) {
        value = in.readByte() == 0x00 ? null : in.readInt();
        color = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (value == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(value);
        }
        dest.writeString(color);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Progress> CREATOR = new Parcelable.Creator<Progress>() {
        @Override
        public Progress createFromParcel(Parcel in) {
            return new Progress(in);
        }

        @Override
        public Progress[] newArray(int size) {
            return new Progress[size];
        }
    };



    @Override
    public String toString() {
        return "Progress{" +
                "value=" + value +
                ", color='" + color + '\'' +
                '}';
    }
}