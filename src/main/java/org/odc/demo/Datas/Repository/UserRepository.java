package org.odc.demo.Datas.Repository;

import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Entity.Role;
import org.odc.demo.Datas.Enums.StatusEnum;
import org.odc.utils.Generics.Repositories.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends SoftDeleteRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByTelephone(String Telephone);

    List<UserEntity> findByRole(Role role);

    List<UserEntity> findByStatus(StatusEnum status);

    List<UserEntity> findByRoleAndStatus(Role role, StatusEnum status);
}
