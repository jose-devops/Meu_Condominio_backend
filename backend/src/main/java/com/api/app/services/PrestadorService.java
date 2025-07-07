package com.api.app.services;

import com.api.app.dtos.PrestadorDTO;
import com.api.app.models.PrestadorModel;
import com.api.app.repositories.PrestadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestadorService {

    @Autowired
    private PrestadorRepository prestadorRepository;

    // Criar prestador a partir do DTO
    public PrestadorModel criarPrestador(PrestadorDTO dto) {
        PrestadorModel model = new PrestadorModel();
        model.setRazao(dto.getRazao());
        model.setCpfCnpj(dto.getCpfCnpj());
        model.setTelefonePrincipal(dto.getTelefonePrincipal());
        model.setTelefoneSecundario(dto.getTelefoneSecundario());
        model.setLinkWhatsapp(dto.getLinkWhatsapp());
        model.setEspecialidade(dto.getEspecialidade());
        model.setObservacao(dto.getObservacao());

        return prestadorRepository.save(model);
    }

    // Listar todos os prestadores
    public List<PrestadorModel> listarPrestadores() {
        return prestadorRepository.findAll();
    }

    public Optional<PrestadorModel> buscarPorId(Long id) {
        return prestadorRepository.findById(id);
    }

    // Atualizar prestador pelo id
    public PrestadorModel atualizarPrestador(Long id, PrestadorDTO dto) {
        Optional<PrestadorModel> optional = prestadorRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        PrestadorModel model = optional.get();
        model.setRazao(dto.getRazao());
        model.setCpfCnpj(dto.getCpfCnpj());
        model.setTelefonePrincipal(dto.getTelefonePrincipal());
        model.setTelefoneSecundario(dto.getTelefoneSecundario());
        model.setLinkWhatsapp(dto.getLinkWhatsapp());
        model.setEspecialidade(dto.getEspecialidade());
        model.setObservacao(dto.getObservacao());

        return prestadorRepository.save(model);
    }

    // Deletar prestador pelo id
    public boolean deletarPrestador(Long id) {
        Optional<PrestadorModel> optional = prestadorRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        prestadorRepository.deleteById(id);
        return true;
    }
}
