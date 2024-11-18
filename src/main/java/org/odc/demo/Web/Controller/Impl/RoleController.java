package org.odc.demo.Web.Controller.Impl;

import org.odc.demo.Datas.Entity.Role;
import org.odc.demo.Datas.Repository.RoleRepository;
import org.odc.demo.Services.Impl.RoleServiceImpl;
import org.odc.demo.Web.Controller.Interfaces.CrudController;
import org.odc.demo.Web.Dtos.Request.RoleDto;
import org.odc.demo.Web.Dtos.Request.RoleUpdateDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController implements CrudController<Role, RoleDto, RoleUpdateDto> {
    @Autowired
    RoleServiceImpl roleService;

    @PostMapping
    @Override
    public ApiResponse<Role> Create(@RequestBody RoleDto roleDto) {
        return roleService.Create(roleDto);
    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse<Role> Update(@PathVariable  Long id,@RequestBody RoleUpdateDto dto) {
        return roleService.Update(id, dto);
    }

    @Override
    @GetMapping
    public ApiResponse<List<Role>> findAll() {
        return roleService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<Role> findById(@PathVariable  Long id) {
        return roleService.findById(id);
    }
}
