package com.remo.restapi.mappers;

import com.remo.restapi.dtos.*;
import com.remo.restapi.models.AppRole;
import com.remo.restapi.models.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IRoleMapper {

    IRoleMapper MAPPER = Mappers.getMapper(IRoleMapper.class);

    RoleDto toRoleDto(AppRole s);

    AppRole toAppRole(RoleDto s);

    @Mappings({
            @Mapping(source = "name", target = "name"),
    })
    AppRole toAppRole(CreateRoleDto s);

    @Mappings({
            @Mapping(source = "name", target = "name"),
    })
    AppRole toAppRole(UpdateRoleDto s);
}