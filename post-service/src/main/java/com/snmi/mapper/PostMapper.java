package com.snmi.mapper;

import com.snmi.dto.PostDTO;
import com.snmi.model.Post;
import com.snmi.projection.PostProjection;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper extends BaseMapper<PostDTO, Post> {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = ("entity.id"))
    @Mapping(target = "title", source = ("entity.title"))
    @Mapping(target = "shortDescription", source = ("entity.shortDescription"))
    @Mapping(target = "content", source = ("entity.content"))
    @Mapping(target = "username", source = ("entity.username"))
    @Mapping(target = "viewCount", source = ("entity.viewCount"))
    @Mapping(target = "likeCount", source = ("entity.likeCount"))
    @Mapping(target = "commentCount", source = ("entity.commentCount"))
    @Mapping(target = "totalRatingPoints", source = ("entity.totalRatingPoints"))
    @Mapping(target = "overallRating", source = ("entity.overallRating"))
    @Mapping(target = "display", source = ("entity.display"))
    @Mapping(target = "createdAt", source = ("entity.createdAt"))
    @Mapping(target = "updatedAt", source = ("entity.updatedAt"))
    PostDTO toDTO(final Post entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = ("dto.title"))
    @Mapping(target = "shortDescription", source = ("dto.shortDescription"))
    @Mapping(target = "content", source = ("dto.content"))
    @Mapping(target = "username", source = ("dto.username"))
    @Mapping(target = "display", constant = "true")
    Post toModel(final PostDTO dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = ("projection.id"))
    @Mapping(target = "title", source = ("projection.title"))
    @Mapping(target = "shortDescription", source = ("projection.shortDescription"))
    @Mapping(target = "username", source = ("projection.username"))
    @Mapping(target = "overallRating", source = ("projection.overallRating"))
    @Mapping(target = "createdAt", source = ("projection.createdAt"))
    PostDTO toDTO(final PostProjection projection);

}
