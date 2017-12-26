package com.tetraandroid.finalappexample.http.apimodel;

import android.os.Parcel;
import android.os.Parcelable;

public class Authenticated implements Parcelable {
    private String token_type;
    private String access_token;

    public String getToken_type() {
        return token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token_type);
        dest.writeString(this.access_token);
    }

    public Authenticated() {
    }

    protected Authenticated(Parcel in) {
        this.token_type = in.readString();
        this.access_token = in.readString();
    }

    public static final Creator<Authenticated> CREATOR = new Creator<Authenticated>() {
        @Override
        public Authenticated createFromParcel(Parcel source) {
            return new Authenticated(source);
        }

        @Override
        public Authenticated[] newArray(int size) {
            return new Authenticated[size];
        }
    };
}
