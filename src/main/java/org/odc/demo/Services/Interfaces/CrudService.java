package org.odc.demo.Services.Interfaces;

import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CrudService<Entity,dto,dtoUpdate> {
    ApiResponse<Entity> Create(dto dto);
    ApiResponse<Entity> Update(Long id,dtoUpdate dto);
    ApiResponse<Entity> Delete(Long id);
    ApiResponse<List<Entity>> findAll();
    ApiResponse<Entity> findById(Long id);
}
