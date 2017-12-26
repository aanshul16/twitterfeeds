package com.tetraandroid.finalappexample.http;

import com.tetraandroid.finalappexample.http.apimodel.Authenticated;

/**
 * Created by MMT5544 on 25-12-2017.
 */

public class AuthenticateEvent {
    private Authenticated authenticated;

    public AuthenticateEvent(Authenticated authenticated) {
        this.authenticated = authenticated;
    }

    public Authenticated getServerResponse() {
        return authenticated;
    }

    public void setServerResponse(Authenticated authenticated) {
        this.authenticated = authenticated;
    }
}
