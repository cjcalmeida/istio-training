package com.cjcalmeida.istio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="modulo", schema = "modulo")
@Data
public class ModuloEntity {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;
    @Column(nullable = false, unique = true)
    private String moduloId;
    @Column(length = 50, nullable = false)
    @NotNull(message = "Nome do Modulo não pode ser nulo")
    private String name;
}