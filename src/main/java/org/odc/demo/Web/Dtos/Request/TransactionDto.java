package org.odc.demo.Web.Dtos.Request;

import jakarta.persistence.Column;
import lombok.Data;
import org.odc.demo.Datas.Enums.TransactionType;

import java.util.Date;

@Data
public class TransactionDto {
    private TransactionType type;

    private Float montant;

    private String senderTelephone;

    private String receiverTelephone;

    private String agentTelephone;

    private Date date;
}
