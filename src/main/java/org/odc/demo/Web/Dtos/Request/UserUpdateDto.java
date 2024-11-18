package org.odc.demo.Web.Dtos.Request;
import lombok.Data;
import org.odc.demo.Datas.Entity.UserEntity;
import org.odc.demo.Datas.Enums.StatusEnum;
import org.odc.utils.Validators.UniqueField;


@Data
public class UserUpdateDto {
    private String nom;
    private String prenom;
    @UniqueField(entity = UserEntity.class,field = "email",message = "l'email doit etre unique")
    private String email;
    private String adresse;
    @UniqueField(entity = UserEntity.class,field = "telephone", message = "ce numero de telephone a deja  ete utilise")
    private String Telephone;
    private String password;
    private String qrCode;

    @UniqueField(entity = UserEntity.class,field = "idCardNumber")
    private String idCardNumber;

    private StatusEnum status;
}