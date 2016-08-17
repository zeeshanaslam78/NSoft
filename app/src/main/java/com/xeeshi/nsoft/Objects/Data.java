package com.xeeshi.nsoft.Objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ZEESHAN on 13/08/16.
 */
@Table(name = "MyProfile")
public class Data extends Model {

    @Column(name = "_id")
    @SerializedName("id")
    @Expose
    private Integer id;

    @Column(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @Column(name = "date")
    @SerializedName("date")
    @Expose
    private String date;

    @Column(name = "photo")
    private String photo;

    @Column(name = "uuid")
    @SerializedName("uid")
    @Expose
    private String uid;

    public Data() {
        super();
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getUserId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The photo path
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo path that is captured from camera
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The uid
     */
    public String getUid() {
        return uid;
    }

    /**
     *
     * @param uid
     * The uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }


    public static Data getSingleItem() {
        return new Select().from(Data.class).executeSingle();
    }

    public static List<Data> getAll() {
        return new Select().from(Data.class).execute();
    }

    public static void deleteAllRecords() {
        new Delete().from(Data.class).execute();
    }


    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", photo='" + photo + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}