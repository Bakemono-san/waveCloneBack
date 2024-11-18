package org.odc.demo.Datas.Repository;

import org.odc.demo.Datas.Entity.Role;
import org.odc.utils.Generics.Repositories.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends SoftDeleteRepository<org.odc.demo.Datas.Entity.Role, Long> {
    // Recherche un rôle par son libellé (ex : "Admin", "Manager", etc.)
    Role findByLibelle(String libelle);
}

