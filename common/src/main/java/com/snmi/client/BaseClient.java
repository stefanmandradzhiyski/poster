package com.snmi.client;

import lombok.Setter;

import static com.snmi.constants.GlobalConstants.BASE_GLOBAL_URL;

@Setter
public abstract class BaseClient {

    protected int port;

    protected String baseURL;

    protected String getBaseURL() {
        return String.format(BASE_GLOBAL_URL, port, baseURL);
    }

}
