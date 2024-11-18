package org.odc.demo.Web.Dtos.Request;

import lombok.Data;
import org.odc.demo.Datas.Enums.AccountType;

@Data
public class AccountUpdateRequestDto {
    private Float solde;

    private AccountType type;

    private Float plafond;
}
