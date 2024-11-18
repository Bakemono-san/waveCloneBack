package org.odc.demo.Web.Controller.Impl;

import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Services.Impl.UserService;
import org.odc.demo.Web.Controller.Interfaces.CrudController;
import org.odc.demo.Web.Dtos.Request.UserRequestDto;
import org.odc.demo.Web.Dtos.Request.UserUpdateDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
public class UserController  implements CrudController<UserEntity, UserRequestDto, UserUpdateDto> {

    @Autowired
    UserService userService;

    @Override
    @PostMapping
    public ApiResponse<UserEntity> Create(@RequestBody UserRequestDto userRequestDto) {
        return userService.Create(userRequestDto);
    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse<UserEntity> Update(@PathVariable Long id,@RequestBody UserUpdateDto dto) {
        return userService.Update(id,dto);
    }

    @Override
    @GetMapping
    public ApiResponse<List<UserEntity>> findAll() {
        return userService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<UserEntity> findById(@PathVariable  Long id) {
        return userService.findById(id);
    }
}
