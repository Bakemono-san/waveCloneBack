package org.odc.demo.Web.Dtos.Request;

import jakarta.persistence.Column;
import lombok.Data;
import org.odc.demo.Datas.Enums.AccountType;

@Data
public class AccountRequestDto {
    private Float solde;

    private AccountType type;

    private Float plafond;
}
