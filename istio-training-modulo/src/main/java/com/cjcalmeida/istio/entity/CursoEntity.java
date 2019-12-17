package com.cjcalmeida.istio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "curso", schema = "modulo")
@Data
public class CursoEntity {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;
    @Column(nullable = false, unique = true)
    @NotNull(message = "Id do curso não pode ser nulo")
    private String cursoId;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "modulo", name = "cursosModulos")
    @NotNull(message = "Modulos do curso não podem ser nulos")
    private List<ModuloEntity> modulos;
}
