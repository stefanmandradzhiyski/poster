package com.snmi.projection;

import java.time.LocalDate;

public interface PostProjection {

    String getId();

    String getTitle();

    String getShortDescription();

    String getUsername();

    Double getOverallRating();

    LocalDate getCreatedAt();

}
