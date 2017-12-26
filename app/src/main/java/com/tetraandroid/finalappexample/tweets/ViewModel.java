package com.tetraandroid.finalappexample.tweets;


import android.os.Parcel;
import android.os.Parcelable;

public class ViewModel implements Parcelable {

    private String content;
    private String userName;
    private String timeCreated;
    private String userProfileImage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeCreated() {
        return timeCreated.substring(0, 19);
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

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
        dest.writeString(this.userName);
        dest.writeString(this.timeCreated);
        dest.writeString(this.userProfileImage);
    }

    public ViewModel() {
    }

    protected ViewModel(Parcel in) {
        this.content = in.readString();
        this.userName = in.readString();
        this.timeCreated = in.readString();
        this.userProfileImage = in.readString();
    }

    public static final Creator<ViewModel> CREATOR = new Creator<ViewModel>() {
        @Override
        public ViewModel createFromParcel(Parcel source) {
            return new ViewModel(source);
        }

        @Override
        public ViewModel[] newArray(int size) {
            return new ViewModel[size];
        }
    };
}
