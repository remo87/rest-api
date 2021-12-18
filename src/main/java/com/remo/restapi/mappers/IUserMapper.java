package com.remo.restapi.mappers;

import com.remo.restapi.dtos.CreateUserDto;
import com.remo.restapi.dtos.UpdateUserDto;
import com.remo.restapi.dtos.UserDetailDto;
import com.remo.restapi.dtos.UserListItemDto;
import com.remo.restapi.models.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {

    IUserMapper MAPPER = Mappers.getMapper(IUserMapper.class);

    UserListItemDto toListItemDto(AppUser s);

    UserDetailDto toUserDetailDto(AppUser s);

    AppUser toAppUser(UserDetailDto s);

    @Mappings({
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "photo", target = "photo"),
            @Mapping(source = "dateOfBirth", target = "dateOfBirth"),
            @Mapping(source = "roles", target = "roles"),
    })
    AppUser toAppUser(CreateUserDto s);

    @Mappings({
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "photo", target = "photo"),
            @Mapping(source = "dateOfBirth", target = "dateOfBirth"),
            @Mapping(source = "roles", target = "roles"),
    })
    AppUser toAppUser(UpdateUserDto s);
}