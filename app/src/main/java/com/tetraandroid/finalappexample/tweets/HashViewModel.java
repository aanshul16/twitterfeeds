package com.tetraandroid.finalappexample.tweets;


import android.os.Parcel;
import android.os.Parcelable;

public class HashViewModel implements Parcelable {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
    }

    public HashViewModel() {
    }

    protected HashViewModel(Parcel in) {
        this.content = in.readString();
    }

    public static final Creator<HashViewModel> CREATOR = new Creator<HashViewModel>() {
        @Override
        public HashViewModel createFromParcel(Parcel source) {
            return new HashViewModel(source);
        }

        @Override
        public HashViewModel[] newArray(int size) {
            return new HashViewModel[size];
        }
    };
}
