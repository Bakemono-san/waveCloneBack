package org.odc.demo.Web.Dtos.Request;

import lombok.Data;
import org.odc.demo.Datas.Entity.Role;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Enums.StatusEnum;
import org.odc.utils.Validators.UniqueField;

@Data
public class UserRequestDto {
    private String nom;
    private String prenom;
    @UniqueField(entity = UserEntity.class,field = "email",message = "l'email doit etre unique")
    private String email;
    @UniqueField(entity = UserEntity.class,field = "Telephone")
    private String telephone;

    private StatusEnum  status;

    private String password;

    private Long role;

    private String idCardNumber;



    private String adresse;
}
