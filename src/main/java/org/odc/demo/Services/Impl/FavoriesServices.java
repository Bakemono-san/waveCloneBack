package org.odc.demo.Services.Impl;


import org.odc.demo.Datas.Entity.Favories;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Repository.FavoriesRepository;
import org.odc.demo.Datas.Repository.UserRepository;
import org.odc.demo.Services.Interfaces.CrudService;
import org.odc.demo.Web.Dtos.Request.FavoriesRequestDto;
import org.odc.demo.Web.Dtos.Request.FavoriesUpdateDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.odc.demo.Web.Dtos.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriesServices implements CrudService<Favories, FavoriesRequestDto, FavoriesUpdateDto> {

    @Autowired
    FavoriesRepository favoriesRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ApiResponse<Favories> Create(FavoriesRequestDto favoriesRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity connectedUser = ((UserDetailImpl) authentication.getPrincipal()).getUserEntity();

        Favories favories = new Favories();
        favories.setName(favoriesRequestDto.getName());
        favories.setTelephone(favoriesRequestDto.getTelephone());
        favoriesRepository.save(favories);

        connectedUser.getFavories().add(favories);
        userRepository.save(connectedUser);

        return new ApiResponse<Favories>(favories,"favorie ajoutee avec success");
    }

    @Override
    public ApiResponse<Favories> Update(Long id, FavoriesUpdateDto dto) {
        return null;
    }

    @Override
    public ApiResponse<Favories> Delete(Long id) {
        return null;
    }

    public ApiResponse<Favories> Delete(String telephone) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity connectedUser = ((UserDetailImpl) authentication.getPrincipal()).getUserEntity();

        Favories favories = favoriesRepository.findByTelephone(telephone).orElse(null);

        if (favories != null) {
            favoriesRepository.delete(favories);
            return new ApiResponse<Favories>(favories,"favorie ajoutee avec success");
        }

        return new ApiResponse<>(null,"cet utilisateur ne figure pas dans ta liste de favories");
    }

    @Override
    public ApiResponse<List<Favories>> findAll() {
        return null;
    }

    @Override
    public ApiResponse<Favories> findById(Long id) {
        return null;
    }
}
