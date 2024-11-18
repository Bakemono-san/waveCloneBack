package org.odc.demo.Datas.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.odc.demo.Datas.Enums.TransactionType;
import org.odc.utils.Generics.Entities.EntityAbstract;

import java.util.Date;

@Data
@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction extends EntityAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private Float montant;

    @Column(name = "sender_telephone")
    private String senderTelephone;

    @Column(name = "receiver_telephone")
    private String receiverTelephone;

    private String agentTelephone;

    @Column(nullable = false)
    private Date date;

    private boolean annulee = false;
}
