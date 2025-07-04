package com.api.app.dtos;

import lombok.Data;

@Data
public class ImovelDTO {


    private String descricao;

    private Long proprietarioId;
    private String endereco;

    private Long moradorId;
    private String cep;

    private String status;
    private String uf;

    private String bairro;
    private String cidade;

    private String numero;
    private String complemento;

    private Double valorAluguel;
    private Double valorCondominio;

    private String observacao;


}
