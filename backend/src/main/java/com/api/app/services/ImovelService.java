package com.api.app.services;

import com.api.app.dtos.ImovelDTO;
import com.api.app.dtos.ImovelResponseDTO;
import com.api.app.models.*;
import com.api.app.models.enums.StatusImovel;
import com.api.app.repositories.ImovelRepository;
import com.api.app.repositories.ProprietarioRepository;
import com.api.app.repositories.MoradorRepository;
import com.api.app.repositories.UsuarioRepository;
import com.api.app.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImovelService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Autowired
    private MoradorRepository inquilinoRepository;

    @Transactional
    public ImovelModel cadastrar(ImovelDTO dto, String token) {

        String email = jwtUtil.extractUsername(token.replace("Bearer ", ""));


        UsuarioModel usuario = usuarioRepository.findByEmailAndAtivoTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado ou inativo"));

        ProprietarioModel proprietario = proprietarioRepository.findByUsuarioAndAtivoTrue(usuario)
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado ou inativo"));

        MoradorModel morador = null;
        if (dto.getMoradorId() != null) {
            morador = moradorRepository.findByIdAndAtivoTrue(dto.getMoradorId())
                    .orElseThrow(() -> new RuntimeException("Morador não encontrado ou inativo"));
        }


        ImovelModel imovel = new ImovelModel();
        imovel.setEndereco(dto.getEndereco());
        imovel.setCep(dto.getCep());
        imovel.setUf(dto.getUf());
        imovel.setCidade(dto.getCidade());
        imovel.setBairro(dto.getBairro());
        imovel.setNumero(dto.getNumero());
        imovel.setComplemento(dto.getComplemento());
        imovel.setValorAluguel(dto.getValorAluguel());
        imovel.setValorCondominio(dto.getValorCondominio());
        imovel.setDescricao(dto.getDescricao());
        imovel.setObservacao(dto.getObservacao());

        try {
            StatusImovel situacao = StatusImovel.valueOf(dto.getStatus());
            imovel.setSituacao(situacao);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + dto.getStatus());
        }

        imovel.setProprietario(proprietario);
        imovel.setMorador(morador);
        imovel.setAtivo(true);

        return imovelRepository.save(imovel);
    }

    public List<ImovelResponseDTO> listarTodos() {
        List<ImovelModel> imoveis = imovelRepository.findAll();

        return imoveis.stream().map(imovel -> {
            ImovelResponseDTO dto = new ImovelResponseDTO();
            dto.setId(imovel.getId());
            dto.setEndereco(imovel.getEndereco());
            dto.setCep(imovel.getCep());
            dto.setCidade(imovel.getCidade());
            dto.setBairro(imovel.getBairro());
            dto.setUf(imovel.getUf());
            dto.setDescricao(imovel.getDescricao());
            dto.setProprietarioId(imovel.getProprietario().getId());
            dto.setNomeProprietario(imovel.getProprietario().getNome());

            if (imovel.getMorador() != null) {
                dto.setMoradorId(imovel.getMorador().getId());
                dto.setNomeMorador(imovel.getMorador().getNome());
            }

            dto.setComplemento(imovel.getComplemento());
            dto.setNumero(imovel.getNumero());
            dto.setValorAluguel(imovel.getValorAluguel());
            dto.setValorCondominio(imovel.getValorCondominio());
            dto.setStatus(imovel.getSituacao().toString());
            dto.setObservacao(imovel.getObservacao());
            return dto;
        }).collect(Collectors.toList());
    }


    public Optional<ImovelModel> buscarPorId(Long id) {
        return imovelRepository.findById(id);
    }





    private ImovelModel converterDTOparaModel(ImovelDTO dto) {
        ImovelModel imovel = new ImovelModel();

        ProprietarioModel proprietario = proprietarioRepository.findById(dto.getProprietarioId())
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));
        imovel.setProprietario(proprietario);

        if (dto.getMoradorId() != null) {
            MoradorModel morador = moradorRepository.findById(dto.getMoradorId())
                    .orElseThrow(() -> new RuntimeException("Morador não encontrado"));
            imovel.setMorador(morador);
        } else {
            imovel.setMorador(null);
        }

        imovel.setDescricao(dto.getDescricao());
        imovel.setEndereco(dto.getEndereco());
        imovel.setCep(dto.getCep());
        imovel.setUf(dto.getUf());
        imovel.setCidade(dto.getCidade());
        imovel.setBairro(dto.getBairro());
        imovel.setNumero(dto.getNumero());
        imovel.setComplemento(dto.getComplemento());
        imovel.setValorAluguel(dto.getValorAluguel());
        imovel.setValorCondominio(dto.getValorCondominio());
        StatusImovel situacao = StatusImovel.valueOf(dto.getStatus());
        imovel.setSituacao(situacao);

        imovel.setObservacao(dto.getObservacao());

        return imovel;
    }

    public void inativar(Long id) {
        ImovelModel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        imovel.setAtivo(false);
        imovelRepository.save(imovel);
    }


    public ImovelModel alterar(Long id, ImovelDTO dto) {
        ImovelModel imovelExistente = imovelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        imovelExistente.setDescricao(dto.getDescricao());
        imovelExistente.setEndereco(dto.getEndereco());
        imovelExistente.setCep(dto.getCep());
        imovelExistente.setCidade(dto.getCidade());
        imovelExistente.setUf(dto.getUf());
        imovelExistente.setBairro(dto.getBairro());
        imovelExistente.setNumero(dto.getNumero());
        imovelExistente.setComplemento(dto.getComplemento());
        imovelExistente.setValorAluguel(dto.getValorAluguel());
        imovelExistente.setValorCondominio(dto.getValorCondominio());
        imovelExistente.setObservacao(dto.getObservacao());
        imovelExistente.setSituacao(StatusImovel.valueOf(dto.getStatus()));


        if (dto.getProprietarioId() != null) {
            ProprietarioModel proprietario = proprietarioRepository.findById(dto.getProprietarioId())
                    .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));
            imovelExistente.setProprietario(proprietario);
        }


        if (dto.getMoradorId() != null) {
            MoradorModel morador = moradorRepository.findById(dto.getMoradorId())
                    .orElseThrow(() -> new RuntimeException("Morador não encontrado"));
            imovelExistente.setMorador(morador);
        }



        return imovelRepository.save(imovelExistente);
    }


    public void deletar(Long id) {
        if (!imovelRepository.existsById(id)) {
            throw new RuntimeException("Imóvel não encontrado com ID: " + id);
        }
        imovelRepository.deleteById(id);
    }

    public List<ImovelModel> listarImoveisParaMorador(Long moradorId) {
        // Buscando imóveis onde o morador é o atribuído ao imóvel
        return imovelRepository.findByMoradorId(moradorId);
    }





}
