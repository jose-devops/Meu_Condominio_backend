package com.api.app.controllers;

import com.api.app.dtos.LoginRequest;
import com.api.app.dtos.LoginResponse;
import com.api.app.dtos.ProprietarioDTO;
import com.api.app.dtos.UsuarioDTO;
import com.api.app.models.UsuarioModel;
import com.api.app.models.enums.TipoAcesso;
import com.api.app.repositories.UsuarioRepository;
import com.api.app.security.JwtUtil;
import com.api.app.services.AuthService;
import com.api.app.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthService authService;

    @PostMapping("/registrar-proprietario")
    public ResponseEntity<?> registrarProprietario(@RequestBody ProprietarioDTO dto) {
        authService.registrarProprietario(dto);
        return ResponseEntity.ok("Proprietário cadastrado com sucesso!");
    }


    @PostMapping("/login-morador")
    public LoginResponse loginMorador(@RequestBody LoginRequest login) {
        Optional<UsuarioModel> user = usuarioRepository.findByEmail(login.getEmail());
        if (user.isPresent() && user.get().getSenha().equals(login.getSenha())) {
            // Verifica se o tipo de usuário é MORADOR
            if (!user.get().getTipoAcesso().equals(TipoAcesso.MORADOR)) {
                throw new RuntimeException("Acesso negado! Somente Morador pode acessar.");
            }

            // Gera o token com base no email e no tipo de acesso (role)
            String token = jwtUtil.generateToken(user.get().getEmail(), user.get().getTipoAcesso().name());
            return new LoginResponse(token);
        }
        throw new RuntimeException("Credenciais inválidas");
    }


    @PostMapping("/login-proprietario")
    public LoginResponse loginProprietario(@RequestBody LoginRequest login) {
        Optional<UsuarioModel> user = usuarioRepository.findByEmail(login.getEmail());
        if (user.isPresent() && user.get().getSenha().equals(login.getSenha())) {
            // Verifica se o tipo de usuário é PROPRIETARIO
            if (!user.get().getTipoAcesso().equals(TipoAcesso.PROPRIETARIO)) {
                throw new RuntimeException("Acesso negado! Somente Proprietário pode acessar.");
            }

            // Gera o token com base no email e no tipo de acesso (role)
            String token = jwtUtil.generateToken(user.get().getEmail(), user.get().getTipoAcesso().name());
            return new LoginResponse(token);
        }
        throw new RuntimeException("Credenciais inválidas");
    }





}