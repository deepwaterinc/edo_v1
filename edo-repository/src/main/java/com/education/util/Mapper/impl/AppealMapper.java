package com.education.util.Mapper.impl;

import com.education.entity.Appeal;
import com.education.model.dto.AppealDto;
import com.education.util.Mapper.Mappable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppealMapper extends Mappable<Appeal, AppealDto> {
}
