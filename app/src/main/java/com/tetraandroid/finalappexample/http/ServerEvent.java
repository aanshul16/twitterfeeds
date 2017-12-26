package com.tetraandroid.finalappexample.http;

import com.tetraandroid.finalappexample.http.apimodel.ServerResponse;

import java.util.List;

/**
 * Created by MMT5544 on 25-12-2017.
 */

public class ServerEvent {
    private List<ServerResponse> serverResponse;

    public ServerEvent(List<ServerResponse> serverResponse) {
        this.serverResponse = serverResponse;
    }

    public List<ServerResponse> getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(List<ServerResponse> serverResponse) {
        this.serverResponse = serverResponse;
    }
}
