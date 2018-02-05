package com.nicklasoxhammar.makeyourownquiz;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nick on 2018-02-05.
 */

public class QuizItem implements Parcelable {

    private String mName; // file name
    private String mFilePath; //file path
    private int mId; //id in database

    public QuizItem() {
    }

    public QuizItem(Parcel in) {
        mName = in.readString();
        mFilePath = in.readString();
        mId = in.readInt();
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public static final Parcelable.Creator<QuizItem> CREATOR = new Parcelable.Creator<QuizItem>() {
        public QuizItem createFromParcel(Parcel in) {
            return new QuizItem(in);
        }
    public QuizItem[] newArray(int size) {
        return new QuizItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mFilePath);
        dest.writeString(mName);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
