package com.api.app.services;

import com.api.app.dtos.ProprietarioDTO;
import com.api.app.dtos.UsuarioDTO;
import com.api.app.models.ProprietarioModel;
import com.api.app.models.enums.TipoAcesso;
import com.api.app.models.enums.TipoProprietario;
import com.api.app.models.UsuarioModel;
import com.api.app.repositories.ProprietarioRepository;
import com.api.app.repositories.UsuarioRepository;
import com.api.app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Autowired
    private JwtUtil jwtUtil;






    @Transactional
    public void registrarProprietario(ProprietarioDTO dto) {
        // Criação do usuário
        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha()); // depois troque por BCrypt!
        usuario.setTipoAcesso(TipoAcesso.PROPRIETARIO);
        usuario.setAtivo(true);
        usuario = usuarioRepository.save(usuario);

        // Criação do proprietário
        ProprietarioModel proprietario = new ProprietarioModel();

        proprietario.setNome(dto.getNome());
        proprietario.setRazaoSocial(dto.getRazaoSocial());
        proprietario.setTipo(TipoProprietario.valueOf(dto.getTipoPessoa()));
        proprietario.setTelefonePrincipal(dto.getTelefonePrincipal());
        proprietario.setTelefoneSecundario(dto.getTelefoneSecundario());
        proprietario.setCpfCnpj(dto.getCpfCnpj());
        proprietario.setEmail(dto.getEmail());
        proprietario.setObservacao(dto.getObservacao());



        proprietario.setUsuario(usuario);
        proprietarioRepository.save(proprietario);
    }



    @Transactional
    public String autenticarMorador(UsuarioDTO usuarioDTO) throws Exception {
        // Verifica se o morador existe no banco de dados
        UsuarioModel usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail())
                .orElseThrow(() -> new Exception("Credenciais inválidas"));

        // Verifica se a senha está correta
        if (!usuario.getSenha().equals(usuarioDTO.getSenha())) {
            throw new Exception("Credenciais inválidas");
        }

        // Gera o token JWT para o morador
        return jwtUtil.generateToken(usuario.getEmail(), "MORADOR");
    }


}
