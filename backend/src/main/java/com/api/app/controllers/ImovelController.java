package com.api.app.controllers;

import com.api.app.dtos.ImovelDTO;
import com.api.app.dtos.ImovelResponseDTO;
import com.api.app.models.ImovelModel;
import com.api.app.models.MoradorModel;
import com.api.app.repositories.MoradorRepository;
import com.api.app.repositories.UsuarioRepository;
import com.api.app.security.JwtUtil;
import com.api.app.services.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/imovel")
public class ImovelController {

    @Autowired
    private ImovelService imovelService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MoradorRepository moradorRepository;

    @PostMapping("/cadastrar")
    public ResponseEntity<ImovelResponseDTO> cadastrar(
            @RequestBody ImovelDTO dto,
            @RequestHeader("Authorization") String token) {

        ImovelModel imovel = imovelService.cadastrar(dto, token);

        // Criação do objeto de resposta
        ImovelResponseDTO response = new ImovelResponseDTO();

        // Preenchendo os campos de resposta
        response.setId(imovel.getId());
        response.setDescricao(imovel.getDescricao());
        response.setProprietarioId(imovel.getProprietario().getId());
        response.setMoradorId(imovel.getMorador() != null ? imovel.getMorador().getId() : null);
        response.setEndereco(imovel.getEndereco());
        response.setCep(imovel.getCep());
        response.setCidade(imovel.getCidade());
        response.setUf(imovel.getUf());
        response.setBairro(imovel.getBairro());
        response.setNumero(imovel.getNumero());
        response.setComplemento(imovel.getComplemento());
        response.setValorAluguel(imovel.getValorAluguel());
        response.setValorCondominio(imovel.getValorCondominio());
        response.setStatus(imovel.getSituacao().toString()); // Certificando-se de que 'situacao' seja convertido corretamente
        response.setObservacao(imovel.getObservacao());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ImovelResponseDTO>> listarTodos() {
        return ResponseEntity.ok(imovelService.listarTodos());
    }


    @GetMapping("/listar-para-morador")
    public ResponseEntity<List<ImovelResponseDTO>> listarImoveisParaMorador(
            @RequestHeader("Authorization") String token) {

        // Extrair o email do morador do token JWT
        String email = jwtUtil.extractUsername(token.replace("Bearer ", ""));

        // Buscar o morador pelo email no banco de dados
        MoradorModel morador = moradorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Morador não encontrado"));

        // Buscar imóveis atribuídos ao morador
        List<ImovelModel> imoveis = imovelService.listarImoveisParaMorador(morador.getId());

        // Mapear os imóveis para resposta
        List<ImovelResponseDTO> resposta = imoveis.stream().map(imovel -> {
            ImovelResponseDTO dto = new ImovelResponseDTO();
            dto.setId(imovel.getId());
            dto.setDescricao(imovel.getDescricao());
            dto.setEndereco(imovel.getEndereco());
            dto.setCep(imovel.getCep());
            dto.setCidade(imovel.getCidade());
            dto.setUf(imovel.getUf());
            dto.setBairro(imovel.getBairro());
            dto.setNumero(imovel.getNumero());
            dto.setComplemento(imovel.getComplemento());
            dto.setValorAluguel(imovel.getValorAluguel());
            dto.setValorCondominio(imovel.getValorCondominio());
            dto.setStatus(imovel.getSituacao().toString());
            dto.setObservacao(imovel.getObservacao());

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImovelModel> buscarPorId(@PathVariable Long id) {
        return imovelService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ImovelResponseDTO> atualizar(@PathVariable Long id, @RequestBody ImovelDTO dto) {
        // Chama o serviço para realizar a atualização
        ImovelModel imovel = imovelService.alterar(id, dto);

        // Criação do objeto de resposta
        ImovelResponseDTO response = new ImovelResponseDTO();
        response.setId(imovel.getId());
        response.setDescricao(imovel.getDescricao());
        response.setEndereco(imovel.getEndereco());
        response.setCep(imovel.getCep());
        response.setCidade(imovel.getCidade());
        response.setUf(imovel.getUf());
        response.setBairro(imovel.getBairro());
        response.setNumero(imovel.getNumero());
        response.setComplemento(imovel.getComplemento());
        response.setValorAluguel(imovel.getValorAluguel());
        response.setValorCondominio(imovel.getValorCondominio());
        response.setStatus(imovel.getSituacao().toString());
        response.setObservacao(imovel.getObservacao());
        response.setProprietarioId(imovel.getProprietario().getId());
        response.setMoradorId(imovel.getMorador() != null ? imovel.getMorador().getId() : null);
        response.setNomeProprietario(imovel.getProprietario().getNome());
        response.setNomeMorador(imovel.getMorador() != null ? imovel.getMorador().getNome() : null);


        // Retorna a resposta com status OK
        return ResponseEntity.ok(response);
    }




    @PreAuthorize("hasRole('ROLE_MORADOR')")
    @DeleteMapping("/inativar/{id}")
    public ResponseEntity<String> inativar(@PathVariable Long id) {
        imovelService.inativar(id);
        return ResponseEntity.ok("Imóvel inativado com sucesso!");
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        imovelService.deletar(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

}
