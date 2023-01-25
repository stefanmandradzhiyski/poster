package com.snmi.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalConstants {

    //Functionalities

    public static final String GET_FUNCTIONALITY = "Get %s functionality";
    public static final String PATCH_FUNCTIONALITY = "Patch %s functionality";
    public static final String CREATE_FUNCTIONALITY = "Create %s functionality";
    public static final String UPDATE_FUNCTIONALITY = "Update %s functionality";
    public static final String DELETE_FUNCTIONALITY = "Delete %s functionality";
    public static final String SEARCH_FUNCTIONALITY = "Search %s functionality";
    public static final String LOG_REQUEST = "%s starts processing a request: %s";
    public static final String KAFKA_SEND_FUNCTIONALITY = "Kafka sends %s to topic %s";
    public static final String LOG_RESPONSE = "%s has successfully processed the request. Response: %s";

    //Values
    public static final String DELIMITER = ",";
    public static final int DEFAULT_NUMBER = 0;
    public static final int DEFAULT_COUNTER = 1;
    public static final String EMPTY_STRING = "";
    public static final int DEFAULT_COUNT_NUMBER = 0;
    public static final String USERNAME = "username";
    public static final String CUSTOM_START_INDEX = "message";
    public static final String CUSTOM_END_INDEX = "statusCode";
    public static final String DEFAULT_START_INDEX = "error";
    public static final String DEFAULT_END_INDEX = "path";

    //Url
    public final static String BASE_GLOBAL_URL = "http://localhost:%s/%s";

    //Messages
    public static final String FORBIDDEN_ACCESS = "You don't have authority to execute that action";
    public static final String CONFLICT_INTERACTION = "A %s for post = %s by username = %s already exists.";

}