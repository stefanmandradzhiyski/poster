package com.snmi.mapper;

import com.snmi.dto.HistoryDTO;
import com.snmi.dto.KafkaHistoryDTO;
import com.snmi.model.History;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HistoryMapper extends BaseMapper<HistoryDTO, History> {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = ("entity.id"))
    @Mapping(target = "postId", source = ("entity.postId"))
    @Mapping(target = "postTitle", source = ("entity.postTitle"))
    @Mapping(target = "username", source = ("entity.username"))
    @Mapping(target = "type", source = ("entity.type"))
    @Mapping(target = "createdAt", source = ("entity.createdAt"))
    @Mapping(target = "updatedAt", source = ("entity.updatedAt"))
    HistoryDTO toDTO(final History entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "postId", source = ("dto.postId"))
    @Mapping(target = "postTitle", source = ("dto.postTitle"))
    @Mapping(target = "username", source = ("dto.username"))
    @Mapping(target = "type", source = ("dto.type"))
    History toModel(final KafkaHistoryDTO dto);

}
