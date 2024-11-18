package org.odc.demo.Datas.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.odc.demo.Datas.Enums.Period;
import org.odc.utils.Generics.Entities.EntityAbstract;

@Data
@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Plannification extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String senderTelephone;

    @Column(nullable = false)
    private String receiverTelephone;

    @Column(nullable = false)
    private float montant;

    @Column(nullable = false)
    private Period period;
}
