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

@Table(name = "StaffJobs")
public class Job extends Model implements Parcelable {

    @Column(name = "position")
    @SerializedName("position")
    @Expose
    private String position;

    @Column(name = "company")
    @SerializedName("company")
    @Expose
    private String company;

    @Column(name = "card")
    @SerializedName("card")
    @Expose
    private String card;

    @Column(name = "salary")
    @SerializedName("salary")
    @Expose
    private String salary;

    @Column(name = "currency")
    @SerializedName("currency")
    @Expose
    private String currency;

    public Job() {
        super();
    }

    /**
     *
     * @return
     * The position
     */
    public String getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     *
     * @return
     * The company
     */
    public String getCompany() {
        return company;
    }

    /**
     *
     * @param company
     * The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * @return
     * The card
     */
    public String getCard() {
        return card;
    }

    /**
     *
     * @param card
     * The card
     */
    public void setCard(String card) {
        this.card = card;
    }

    /**
     *
     * @return
     * The salary
     */
    public String getSalary() {
        return salary;
    }

    /**
     *
     * @param salary
     * The salary
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     *
     * @return
     * The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     * The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }



    protected Job(Parcel in) {
        position = in.readString();
        company = in.readString();
        card = in.readString();
        salary = in.readString();
        currency = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(position);
        dest.writeString(company);
        dest.writeString(card);
        dest.writeString(salary);
        dest.writeString(currency);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };



    @Override
    public String toString() {
        return "Job{" +
                "position='" + position + '\'' +
                ", company='" + company + '\'' +
                ", card='" + card + '\'' +
                ", salary='" + salary + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
