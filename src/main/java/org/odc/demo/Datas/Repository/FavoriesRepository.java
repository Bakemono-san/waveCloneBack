package org.odc.demo.Datas.Repository;

import org.odc.demo.Datas.Entity.Favories;
import org.odc.utils.Generics.Repositories.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface FavoriesRepository extends SoftDeleteRepository<Favories,Long> {
    Optional<Favories> findByTelephone(String telephone);

}
