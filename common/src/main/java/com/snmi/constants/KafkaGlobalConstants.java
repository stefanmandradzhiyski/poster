package com.snmi.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaGlobalConstants {

    //Topics
    public static final String USER_TOPIC = "user.post.count";
    public static final String HISTORY_TOPIC = "history.interaction";

}
