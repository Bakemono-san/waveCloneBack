package org.odc.demo.Services.Impl;

import org.odc.demo.Datas.Entity.Role;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Enums.StatusEnum;
import org.odc.demo.Datas.Repository.RoleRepository;
import org.odc.demo.Datas.Repository.UserRepository;
import org.odc.demo.Services.Interfaces.CrudService;
import org.odc.demo.Web.Dtos.Request.UserRequestDto;
import org.odc.demo.Web.Dtos.Request.UserUpdateDto;
import org.odc.demo.Web.Dtos.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CrudService<UserEntity, UserRequestDto, UserUpdateDto> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Rechercher un utilisateur par email
    public ApiResponse<UserEntity> getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new ApiResponse<>(user, "User found");
    }

    // Rechercher les utilisateurs par rôle
    public ApiResponse<List<UserEntity>> getUsersByRole(String libelle) {
        Role role = roleRepository.findByLibelle(libelle);
        List<UserEntity> users = userRepository.findByRole(role);
        return new ApiResponse<>(users, "Users found for role: " + libelle);
    }

    // Rechercher les utilisateurs par statut
    public ApiResponse<List<UserEntity>> getUsersByStatus(StatusEnum status) {
        List<UserEntity> users = userRepository.findByStatus(status);
        return new ApiResponse<>(users, "Users found for status: " + status);
    }

    // Rechercher les utilisateurs par rôle et statut
    public ApiResponse<List<UserEntity>> getUsersByRoleAndStatus(String libelle, StatusEnum status) {
        Role role = roleRepository.findByLibelle(libelle);
        List<UserEntity> users = userRepository.findByRoleAndStatus(role, status);
        return new ApiResponse<>(users, "Users found for role: " + libelle + " and status: " + status);
    }

    // Lister tous les utilisateurs (y compris ceux supprimés)
    public ApiResponse<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return new ApiResponse<>(users, "All users retrieved");
    }

    // Afficher tous les utilisateurs avec leurs détails
    public void getAllUsersWithDetails() {
        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            System.out.println("Utilisateur: " + user.getNom() + " " + user.getPrenom());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Téléphone: " + user.getTelephone()); // Assurez-vous que cela correspond à 'telephone'
            System.out.println("Statut: " + user.getStatus());
            System.out.println("Rôle: " + user.getRole().getLibelle());
            System.out.println("==================================");
        }
    }

    @Override
    public ApiResponse<UserEntity> Create(UserRequestDto userRequestDto) {
        // Handle user creation (returning ApiResponse)
        UserEntity newUser = new UserEntity();
        // Populate newUser from userRequestDto
        UserEntity createdUser = userRepository.save(newUser);
        return new ApiResponse<>(createdUser, "User created successfully");
    }

    @Override
    public ApiResponse<UserEntity> Update(Long id, UserUpdateDto dto) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            UserEntity updatedUser = userOpt.get();
            // Update fields from dto
            updatedUser = userRepository.save(updatedUser);
            return new ApiResponse<>(updatedUser, "User updated successfully");
        }
        return new ApiResponse<>(null, "User not found");
    }

    @Override
    public ApiResponse<UserEntity> Delete(Long id) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            return new ApiResponse<>(userOpt.get(), "User deleted successfully");
        }
        return new ApiResponse<>(null, "User not found");
    }

    @Override
    public ApiResponse<List<UserEntity>> findAll() {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new ApiResponse<>(null, "No users found");
        }
        return new ApiResponse<>(users, "Users retrieved successfully");
    }

    @Override
    public ApiResponse<UserEntity> findById(Long id) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return new ApiResponse<>(userOpt.get(), "User found");
        }
        return new ApiResponse<>(null, "User not found");
    }

    public ApiResponse<UserEntity> findByTelephone(String Tel){
        Optional<UserEntity> userOpt = userRepository.findByTelephone(Tel);

        if(userOpt.isPresent()){
            return new ApiResponse<>(userOpt.get(), "User found");
        }

        return new ApiResponse<>(null, "User not found");
    }
}
