package com.xeeshi.nsoft.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZEESHAN on 13/08/16.
 */
@Table(name = "Staff")
public class User extends Model implements Parcelable {

    @Column(name = "_id")
    @SerializedName("id")
    @Expose
    private String id;

    @Column(name = "first_name")
    @SerializedName("first_name")
    @Expose
    private String firstName;

    @Column(name = "last_name")
    @SerializedName("last_name")
    @Expose
    private String lastName;

    @Column(name = "email")
    @SerializedName("email")
    @Expose
    private String email;

    @Column(name = "city")
    @SerializedName("city")
    @Expose
    private String city;

    @Column(name = "country")
    @SerializedName("country")
    @Expose
    private String country;

    @Column(name = "date")
    @SerializedName("date")
    @Expose
    private String date;

    @Column(name = "map_id")
    @SerializedName("map")
    @Expose
    private Map map;

    @Column(name = "skills")
    @SerializedName("skills")
    @Expose
    private List<String> skills = new ArrayList<String>();

    @Column(name = "avatar")
    @SerializedName("avatar")
    @Expose
    private String avatar;

    @Column(name = "progress_id")
    @SerializedName("progress")
    @Expose
    private Progress progress;

    @Column(name = "job_id")
    @SerializedName("job")
    @Expose
    private Job job;

    @Column(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    public User() {
        super();
    }

    /**
     *
     * @return
     * The id
     */
    public String getUserId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
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
     * The map
     */
    public Map getMap() {
        return map;
    }

    /**
     *
     * @param map
     * The map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     *
     * @return
     * The skills
     */
    public List<String> getSkills() {
        return skills;
    }

    /**
     *
     * @param skills
     * The skills
     */
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    /**
     *
     * @return
     * The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     *
     * @param avatar
     * The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     *
     * @return
     * The progress
     */
    public Progress getProgress() {
        return progress;
    }

    /**
     *
     * @param progress
     * The progress
     */
    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    /**
     *
     * @return
     * The job
     */
    public Job getJob() {
        return job;
    }

    /**
     *
     * @param job
     * The job
     */
    public void setJob(Job job) {
        this.job = job;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }



    protected User(Parcel in) {
        id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        city = in.readString();
        country = in.readString();
        date = in.readString();
        map = (Map) in.readValue(Map.class.getClassLoader());
        if (in.readByte() == 0x01) {
            skills = new ArrayList<String>();
            in.readList(skills, String.class.getClassLoader());
        } else {
            skills = null;
        }
        avatar = in.readString();
        progress = (Progress) in.readValue(Progress.class.getClassLoader());
        job = (Job) in.readValue(Job.class.getClassLoader());
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(date);
        dest.writeValue(map);
        if (skills == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(skills);
        }
        dest.writeString(avatar);
        dest.writeValue(progress);
        dest.writeValue(job);
        dest.writeString(description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public static User getSingleItem() {
        return new Select().from(User.class).executeSingle();
    }

    public static List<User> getAll() {
        return new Select().from(User.class).execute();
    }

    public static void deleteAllRecords() {
        new Delete().from(User.class).execute();
    }

    public static void deleteSingleItemBasedOnId(long id) {

        User item = User.load(User.class, id);
        item.delete();

        new Delete().from(Job.class).where("Id = ?", item.getJob().getId()).execute();
        new Delete().from(Progress.class).where("Id = ?", item.getProgress().getId()).execute();
        new Delete().from(Map.class).where("Id = ?", item.getMap().getId()).execute();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", date='" + date + '\'' +
                ", map=" + map +
                ", skills=" + skills +
                ", avatar='" + avatar + '\'' +
                ", progress=" + progress +
                ", job=" + job +
                ", description='" + description + '\'' +
                '}';
    }
}