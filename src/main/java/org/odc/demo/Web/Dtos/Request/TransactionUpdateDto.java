package org.odc.demo.Web.Dtos.Request;

import lombok.Data;
import org.odc.demo.Datas.Enums.TransactionType;

import java.util.Date;

@Data
public class TransactionUpdateDto {
    private  boolean annulee;
}
