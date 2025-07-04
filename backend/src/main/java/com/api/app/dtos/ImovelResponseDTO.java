package com.api.app.dtos;

import lombok.Data;

@Data
public class ImovelResponseDTO {
    private Long id;

    private String descricao;

    private Long proprietarioId;
    private String nomeProprietario; // <- adicione
    private Long moradorId;
    private String nomeMorador; // <- adicione

    // Adicionado moradorId
    private String endereco;
    private String cep;
    private String cidade;
    private String uf;
    private String bairro;
    private String numero;// Adicionado numero
    private String complemento;  // Adicionado complemento
    private Double valorAluguel;  // Adicionado valorAluguel
    private Double valorCondominio;  // Adicionado valorCondominio
    private String observacao;  // Adicionado observacao
    private String status;
}
