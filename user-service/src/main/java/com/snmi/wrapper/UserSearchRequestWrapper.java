package com.snmi.wrapper;

import com.snmi.dto.UserSearchRequestDTO;
import com.snmi.enums.SortDirection;
import com.snmi.enums.SortUserType;
import com.snmi.util.QueryParamUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class UserSearchRequestWrapper {

    private final String username;

    private final String sortType;

    private final String sortDirection;

    public UserSearchRequestWrapper(final UserSearchRequestDTO searchRequestDTO) {
        this.username = searchRequestDTO.getUsername();
        this.sortType = QueryParamUtil.getStringOrDefault(searchRequestDTO.getSortType().name(), SortUserType.USERNAME.name());
        this.sortDirection = QueryParamUtil.getStringOrDefault(searchRequestDTO.getSortDirection().name(), SortDirection.ASC.name());
    }

}
