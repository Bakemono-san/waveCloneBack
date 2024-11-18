package org.odc.demo.Web.Controller.Interfaces;

import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CrudController<Entity,dto,dtoUpdae> {
    ApiResponse Create(dto dto);
    ApiResponse Update(Long id,dtoUpdae dto);
    ApiResponse findAll();
    ApiResponse findById(Long id);
}
