package com.snmi.mapper;

import com.snmi.dto.UserDTO;
import com.snmi.model.User;
import com.snmi.projection.UserProjection;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDTO, User> {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = ("entity.id"))
    @Mapping(target = "username", source = ("entity.username"))
    @Mapping(target = "visiblePostCount", source = ("entity.visiblePostCount"))
    @Mapping(target = "totalPostCount", source = ("entity.totalPostCount"))
    @Mapping(target = "active", source = ("entity.active"))
    @Mapping(target = "createdAt", source = ("entity.createdAt"))
    @Mapping(target = "updatedAt", source = ("entity.updatedAt"))
    UserDTO toDTO(final User entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", source = ("dto.username"))
    @Mapping(target = "password", source = ("dto.password"))
    @Mapping(target = "active", constant = ("true"))
    User toModel(final UserDTO dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = ("projection.id"))
    @Mapping(target = "username", source = ("projection.username"))
    @Mapping(target = "visiblePostCount", source = ("projection.postCount"))
    UserDTO toDTO(final UserProjection projection);

}
