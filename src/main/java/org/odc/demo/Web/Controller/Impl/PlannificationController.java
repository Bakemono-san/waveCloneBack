package org.odc.demo.Web.Controller.Impl;

import org.odc.demo.Datas.Entity.Plannification;
import org.odc.demo.Services.Impl.PlannificationServiceImpl;
import org.odc.demo.Web.Controller.Interfaces.CrudController;
import org.odc.demo.Web.Dtos.Request.PlannificationRequestDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Planning")
public class PlannificationController implements CrudController<Plannification, PlannificationRequestDto,Object> {
    @Autowired
    PlannificationServiceImpl plannificationService;

    @Override
    @PostMapping
    public ApiResponse<Plannification> Create(@RequestBody PlannificationRequestDto o) {
        return plannificationService.Create(o);
    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse<Plannification> Update(@PathVariable Long id,@RequestBody Object dto) {
        return plannificationService.Update(id, dto);
    }

    @Override
    @GetMapping
    public ApiResponse<List<Plannification>> findAll() {
        return plannificationService.findAll();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Plannification> annuler(@PathVariable Long id) {
        return plannificationService.Delete(id);
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<Plannification> findById(@PathVariable  Long id) {
        return plannificationService.findById(id);
    }
}
