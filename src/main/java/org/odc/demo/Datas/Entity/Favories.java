package org.odc.demo.Datas.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.odc.utils.Generics.Entities.EntityAbstract;

import java.util.Set;

@Data
@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Favories extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String telephone;

    @ManyToMany(mappedBy = "favories", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<UserEntity> users;
}
