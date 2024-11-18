package org.odc.demo.Services.Impl;

import org.odc.demo.Datas.Entity.Role;
import org.odc.demo.Datas.Repository.RoleRepository;
import org.odc.demo.Services.Interfaces.CrudService;
import org.odc.demo.Web.Dtos.Request.RoleDto;
import org.odc.demo.Web.Dtos.Request.RoleUpdateDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements CrudService<Role, RoleDto, RoleUpdateDto> {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public ApiResponse<Role> Create(RoleDto o) {
        Role role = new Role();
        role.setLibelle(o.getLibelle());

        Role createdRole = roleRepository.save(role);
        return new ApiResponse<Role>(createdRole, "role created");
    }

    @Override
    public ApiResponse<Role> Update(Long id, RoleUpdateDto dto) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            role.get().setLibelle(dto.getLibelle());
            Role updatedRole = roleRepository.save(role.get());
            return new ApiResponse<Role>(updatedRole, "role updated");
        }

        return new ApiResponse<>(null, "le role que vous voulez modifier n'existe pas");
    }

    @Override
    public ApiResponse<Role> Delete(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            roleRepository.delete(role.get());
            return new ApiResponse<>(role.get(), "role deleted");
        }

        return new ApiResponse<>(null, "le role que vous voulez supprimer n'existe pas");
    }

    @Override
    public ApiResponse<List<Role>> findAll() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            return new ApiResponse<>(null, "no roles found");
        }
        return new ApiResponse<>(roles, "roles retrieved successfully");
    }

    @Override
    public ApiResponse<Role> findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return new ApiResponse<>(role.get(), "role found");
        }

        return new ApiResponse<>(null, "role not found");
    }
}
