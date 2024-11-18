package org.odc.demo.Datas.Repository;

import org.odc.demo.Datas.Entity.Plannification;
import org.odc.utils.Generics.Repositories.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannificationRepository extends SoftDeleteRepository<Plannification,Long> {
}
