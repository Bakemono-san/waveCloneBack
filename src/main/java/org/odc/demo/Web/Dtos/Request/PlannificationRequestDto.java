package org.odc.demo.Web.Dtos.Request;

import jakarta.persistence.Column;
import lombok.Data;
import org.odc.demo.Datas.Enums.Period;

@Data
public class PlannificationRequestDto {

    private String receiverTelephone;

    private float montant;

    private Period period;
}
