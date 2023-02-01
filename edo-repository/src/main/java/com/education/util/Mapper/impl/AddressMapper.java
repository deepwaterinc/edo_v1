package com.education.util.Mapper.impl;

import com.education.entity.Address;
import com.education.model.dto.AddressDto;
import com.education.util.Mapper.Mappable;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AddressMapper extends Mappable<Address, AddressDto> {
}

