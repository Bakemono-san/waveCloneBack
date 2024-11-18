package org.odc.demo.Web.Dtos.Response;

import lombok.Data;
import org.odc.demo.Datas.Entity.UserEntity;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UserEntity user;
    private float solde;
}
