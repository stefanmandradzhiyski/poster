package com.snmi.mapper;

import com.snmi.dto.PostDTO;
import com.snmi.dto.UserDTO;
import com.snmi.dto.UserProfileDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DomainMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = ("user.id"))
    @Mapping(target = "username", source = ("user.username"))
    @Mapping(target = "visiblePostCount", source = ("user.visiblePostCount"))
    @Mapping(target = "totalPostCount", source = ("user.totalPostCount"))
    @Mapping(target = "active", source = ("user.active"))
    @Mapping(target = "createdAt", source = ("user.createdAt"))
    @Mapping(target = "updatedAt", source = ("user.updatedAt"))
    @Mapping(target = "posts", source = ("posts"))
    UserProfileDTO toUserProfileDTO(final UserDTO user, final List<PostDTO> posts);

}
