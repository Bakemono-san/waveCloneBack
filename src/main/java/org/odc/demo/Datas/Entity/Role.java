package org.odc.demo.Datas.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.odc.utils.Generics.Entities.EntityAbstract;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@ToString
public class Role extends EntityAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

}
