package edu.uga.cs.project4;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable{
    // instance variables for columns in table
    private long   id;
    private String stateName;
    private String capitalCity;
    private String secondCity;
    private String thirdCity;

    // POJO Constructor
    public Question()
    {
        this.id = -1;
        this.stateName = null;
        this.capitalCity = null;
        this.secondCity = null;
        this.thirdCity = null;
    }

    public Question( String stateName, String capitalCity, String secondCity, String thirdCity ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.stateName = stateName;
        this.capitalCity = capitalCity;
        this.secondCity = secondCity;
        this.thirdCity = thirdCity;
    }

    protected Question(Parcel in) {
        id = in.readLong();
        stateName = in.readString();
        capitalCity = in.readString();
        secondCity = in.readString();
        thirdCity = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        /**
         * Creating a new instance of the Parcelable class
         *
         * @param in
         * @return
         */
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        /**
         * Create a new array of the Parcelable class.
         *
         * @param size
         * @return
         */
        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getStateName()
    {
        return stateName;
    }

    public void setStateName(String stateName)
    {
        this.stateName = stateName;
    }

    public String getCapitalCity()
    {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity)
    {
        this.capitalCity = capitalCity;
    }

    public String getSecondCity()
    {
        return secondCity;
    }

    public void setSecondCity(String secondCity)
    {
        this.secondCity = secondCity;
    }

    public String getThirdCity()
    {
        return thirdCity;
    }

    public void setThirdCity(String thirdCity)
    {
        this.thirdCity = thirdCity;
    }

    public String toString()
    {
        return id + ": " + stateName + " " + capitalCity + " " + secondCity + " " + thirdCity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @param parcel the parcel in which the object should be written
     * @param i additional flags
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(stateName);
        parcel.writeString(capitalCity);
        parcel.writeString(secondCity);
        parcel.writeString(thirdCity);
    }
}


