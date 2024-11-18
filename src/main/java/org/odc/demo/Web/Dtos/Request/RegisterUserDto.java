package org.odc.demo.Web.Dtos.Request;

import lombok.Data;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Enums.StatusEnum;
import org.odc.utils.Validators.UniqueField;

@Data
public class RegisterUserDto {

    private String nom;
    private String prenom;
    private String adresse;
    @UniqueField(entity = UserEntity.class,field = "Telephone",message = "le telephone doit etre unique")
    private String Telephone;
    @UniqueField(entity = UserEntity.class,field = "email",message = "l'email doit etre unique")
    private String email;

    private String password;

    private StatusEnum status;

    private String photo;

    private Long roleId;
}
