package com.api.app.controllers;

import com.api.app.dtos.PrestadorDTO;
import com.api.app.dtos.PrestadorResponseDTO;
import com.api.app.models.PrestadorModel;
import com.api.app.services.PrestadorService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prestadores")
public class PrestadorController {

    @Autowired
    private PrestadorService prestadorService;

    // Converter Model para ResponseDTO
    private PrestadorResponseDTO toResponseDTO(PrestadorModel model) {
        return new PrestadorResponseDTO(
                model.getId(),
                model.getRazao(),
                model.getCpfCnpj(),
                model.getTelefonePrincipal(),
                model.getTelefoneSecundario(),
                model.getLinkWhatsapp(),
                model.getEspecialidade(),
                model.getObservacao()
        );
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarPrestador(@Valid @RequestBody PrestadorDTO dto,
                                            Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PROPRIETARIO"))) {
            return ResponseEntity.status(403).body("Acesso negado");
        }

        PrestadorModel novo = prestadorService.criarPrestador(dto);
        return ResponseEntity.ok(toResponseDTO(novo));
    }


    @GetMapping("/listar")
    public ResponseEntity<?> listarPrestadores(@AuthenticationPrincipal UserDetails user) {

        List<PrestadorModel> prestadores = prestadorService.listarPrestadores();
        List<PrestadorResponseDTO> response = prestadores.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> buscarPrestadorPorId(@PathVariable Long id,
                                                  Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PROPRIETARIO") ||
                        a.getAuthority().equals("ROLE_MORADOR"))) {
            return ResponseEntity.status(403).body("Acesso negado");
        }

        Optional<PrestadorModel> prestadorOpt = prestadorService.buscarPorId(id);
        if (prestadorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PrestadorResponseDTO dto = toResponseDTO(prestadorOpt.get());
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarPrestador(@PathVariable Long id,
                                                @Valid @RequestBody PrestadorDTO dto,
                                                Authentication authentication) {

        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PROPRIETARIO") || a.getAuthority().equals("ROLE_MORADOR") )) {
            return ResponseEntity.status(403).body("Acesso negado");
        }

        PrestadorModel atualizado = prestadorService.atualizarPrestador(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponseDTO(atualizado));
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarPrestador(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PROPRIETARIO"))) {
            return ResponseEntity.status(403).body("Acesso negado");
        }

        boolean removido = prestadorService.deletarPrestador(id);
        if (!removido) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Prestador deletado com sucesso!");
    }
}
