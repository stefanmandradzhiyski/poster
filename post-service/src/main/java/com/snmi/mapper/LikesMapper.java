package com.snmi.mapper;

import com.snmi.dto.LikeDTO;
import com.snmi.model.Likes;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LikesMapper extends BaseMapper<LikeDTO, Likes> {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = ("entity.id"))
    @Mapping(target = "postId", source = ("entity.postId"))
    @Mapping(target = "username", source = ("entity.username"))
    @Mapping(target = "createdAt", source = ("entity.createdAt"))
    @Mapping(target = "updatedAt", source = ("entity.updatedAt"))
    LikeDTO toDTO(final Likes entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "postId", source = ("dto.postId"))
    @Mapping(target = "username", source = ("dto.username"))
    Likes toModel(final LikeDTO dto);

}
