package org.odc.demo.Services.Impl;

import org.odc.demo.Datas.Entity.Plannification;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Repository.PlannificationRepository;
import org.odc.demo.Datas.Repository.UserRepository;
import org.odc.demo.Services.Interfaces.CrudService;
import org.odc.demo.Web.Dtos.Request.PlannificationRequestDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.odc.demo.Web.Dtos.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlannificationServiceImpl implements CrudService<Plannification, PlannificationRequestDto, Object> {

    @Autowired
    PlannificationRepository plannificationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public ApiResponse<Plannification> Create(PlannificationRequestDto o) {
        try {
            // Get authentication context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Check if authentication is null or invalid
            if (authentication == null) {
                System.out.println("Authentication is null");
                return new ApiResponse<>(null, "Authentication failed. Please log in.");
            }

            // Get the connected user
            UserEntity connectedUser = ((UserDetailImpl) authentication.getPrincipal()).getUserEntity();
            System.out.println("User connected: " + connectedUser.getRole());

           return new ApiResponse<>(null,"fehzkf");

        } catch (Exception e) {
            // Catch any exception and log it
            e.printStackTrace();
            return new ApiResponse<>(null, "An error occurred while creating the plannification.");
        }
    }

    @Override
    public ApiResponse<Plannification> Update(Long id, Object dto) {
        Optional<Plannification> plannification = plannificationRepository.findById(id);
        if (plannification.isPresent()) {
            // Implement logic to update fields of plannification
            // If you have a specific DTO for updating, you should cast 'dto' here to that type.
            Plannification updatedPlannification = plannification.get();
            // Example: updatedPlannification.setPeriod(((PlannificationUpdateDto)dto).getPeriod());
            // Save the updated entity
            Plannification savedPlannification = plannificationRepository.save(updatedPlannification);
            return new ApiResponse<>(savedPlannification, "Plannification updated successfully");
        }

        return new ApiResponse<>(null, "Plannification not found");
    }

    @Override
    public ApiResponse<Plannification> Delete(Long id) {
        Plannification plannification = plannificationRepository.findById(id).orElse(null);
        if (plannification != null) {
            plannificationRepository.deleteById(plannification.getId());
            return new ApiResponse<>(plannification, "Plannification deleted successfully");
        }
        return new ApiResponse<>(null, "Plannification not found");
    }

    @Override
    public ApiResponse<List<Plannification>> findAll() {
        List<Plannification> plannifications = plannificationRepository.findAll();
        if (plannifications.isEmpty()) {
            return new ApiResponse<>(null, "No plannifications found");
        }
        return new ApiResponse<>(plannifications, "Plannifications retrieved successfully");
    }

    @Override
    public ApiResponse<Plannification> findById(Long id) {
        Optional<Plannification> plannification = plannificationRepository.findById(id);
        if (plannification.isPresent()) {
            return new ApiResponse<>(plannification.get(), "Plannification found");
        }
        return new ApiResponse<>(null, "Plannification not found");
    }
}
