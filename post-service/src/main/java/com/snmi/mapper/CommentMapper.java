package com.snmi.mapper;

import com.snmi.dto.CommentDTO;
import com.snmi.model.Comment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper extends BaseMapper<CommentDTO, Comment> {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = ("entity.id"))
    @Mapping(target = "postId", source = ("entity.postId"))
    @Mapping(target = "username", source = ("entity.username"))
    @Mapping(target = "content", source = ("entity.content"))
    @Mapping(target = "rating", source = ("entity.rating"))
    @Mapping(target = "createdAt", source = ("entity.createdAt"))
    @Mapping(target = "updatedAt", source = ("entity.updatedAt"))
    CommentDTO toDTO(final Comment entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "postId", source = ("dto.postId"))
    @Mapping(target = "username", source = ("dto.username"))
    @Mapping(target = "content", source = ("dto.content"))
    @Mapping(target = "rating", source = ("dto.rating"))
    Comment toModel(final CommentDTO dto);

}
