package org.odc.demo.Web.Dtos.Mapper;

import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Web.Dtos.Request.UserRequestDto;
import org.odc.demo.Web.Dtos.Response.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserRequestDto dto);

    UserResponseDto toDto(UserEntity entity);
}
