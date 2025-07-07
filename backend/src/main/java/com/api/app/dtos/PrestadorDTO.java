package com.api.app.dtos;
import com.api.app.models.enums.Especialidade;
import lombok.Data;

@Data
public class PrestadorDTO {

    //@NotBlank(message = "Razão é obrigatória")
    private String razao;

    //@NotBlank(message = "CPF/CNPJ é obrigatório")
    private String cpfCnpj;

    private String telefonePrincipal;

    private String telefoneSecundario;

    private String linkWhatsapp;

    private Especialidade especialidade;

    private String observacao;


}