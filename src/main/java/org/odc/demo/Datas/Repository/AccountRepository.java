package org.odc.demo.Datas.Repository;

import org.odc.demo.Datas.Entity.Account;
import org.odc.utils.Generics.Repositories.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends SoftDeleteRepository<Account,Long> {
}
