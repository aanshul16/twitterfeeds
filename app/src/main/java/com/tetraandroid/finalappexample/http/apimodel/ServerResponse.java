package com.tetraandroid.finalappexample.http.apimodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ServerResponse implements Parcelable {

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("created_at")
    @Expose
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ServerResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeParcelable(this.user, flags);
        dest.writeString(this.timeStamp);
    }

    protected ServerResponse(Parcel in) {
        this.text = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.timeStamp = in.readString();
    }

    public static final Creator<ServerResponse> CREATOR = new Creator<ServerResponse>() {
        @Override
        public ServerResponse createFromParcel(Parcel source) {
            return new ServerResponse(source);
        }

        @Override
        public ServerResponse[] newArray(int size) {
            return new ServerResponse[size];
        }
    };
}
