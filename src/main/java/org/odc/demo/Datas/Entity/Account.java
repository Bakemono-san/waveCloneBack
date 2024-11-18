package org.odc.demo.Datas.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.odc.demo.Datas.Enums.AccountType;
import org.odc.utils.Generics.Entities.EntityAbstract;

@Data
@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Account extends EntityAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float solde;

    @Column(nullable = false)
    private AccountType type;

    private Float plafond;

    private boolean plafonnee;

    private Float sommeDepot;


    @OneToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
