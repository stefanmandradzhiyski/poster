package com.snmi.wrapper;

import com.snmi.dto.PostSearchRequestDTO;
import com.snmi.enums.SortDirection;
import com.snmi.enums.SortPostType;
import com.snmi.util.QueryParamUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class PostSearchRequestWrapper {

    private final String title;

    private final String username;

    private final int minimumOverallRating;

    private final boolean inactiveIncluded;

    private final String sortType;

    private final String sortDirection;

    public PostSearchRequestWrapper(final PostSearchRequestDTO searchRequest) {
        this.title = QueryParamUtil.getStringOrEmpty(searchRequest.getTitle());
        this.username = QueryParamUtil.getStringOrEmpty(searchRequest.getUsername());
        this.minimumOverallRating = QueryParamUtil.getNumberOrZero(searchRequest.getMinimumOverallRating());
        this.inactiveIncluded = searchRequest.isInactiveIncluded();
        this.sortType = QueryParamUtil.getStringOrDefault(searchRequest.getSortType().name(), SortPostType.CREATION_DATE.name());
        this.sortDirection = QueryParamUtil.getStringOrDefault(searchRequest.getSortDirection().name(), SortDirection.ASC.name());
    }

}
