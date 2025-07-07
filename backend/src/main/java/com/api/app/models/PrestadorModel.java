package com.api.app.models;

import com.api.app.models.enums.Especialidade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prestadores")
@Data
public class PrestadorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razao;

    @Column(unique = true)
    private String cpfCnpj;

    private String telefonePrincipal;

    private String telefoneSecundario;

    private String linkWhatsapp;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Column(columnDefinition = "TEXT")
    private String observacao;
}
