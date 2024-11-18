package org.odc.demo.Web.Dtos.Request;

import lombok.Data;

@Data
public class TransfertGroupeDto {
    private float montant;

    private String[] ids;
}
