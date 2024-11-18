package org.odc.demo.Web.Controller.Impl;

import org.odc.demo.Datas.Entity.Favories;
import org.odc.demo.Services.Impl.FavoriesServices;
import org.odc.demo.Web.Controller.Interfaces.CrudController;
import org.odc.demo.Web.Dtos.Request.FavoriesRequestDto;
import org.odc.demo.Web.Dtos.Request.FavoriesUpdateDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/Favories")
public class FavoriesController implements CrudController<Favories, FavoriesRequestDto, FavoriesUpdateDto> {

    @Autowired
    FavoriesServices favoriesServices;

    @Override
    @PostMapping
    public ApiResponse<Favories> Create(@RequestBody FavoriesRequestDto favoriesRequestDto) {
        return favoriesServices.Create(favoriesRequestDto);
    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse<Favories> Update(@PathVariable Long id, FavoriesUpdateDto dto) {
        return favoriesServices.Update(id,dto);
    }

    @Override
    @GetMapping
    public ApiResponse<List<Favories>> findAll() {
        return favoriesServices.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<Favories> findById(@PathVariable Long id) {
        return favoriesServices.findById(id);
    }

    @DeleteMapping("/{id}")
    public  ApiResponse<Favories> delete(String telephone) {
        return favoriesServices.Delete(telephone);
    }
}
