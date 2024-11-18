package org.odc.demo.Web.Dtos.Response;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
}
