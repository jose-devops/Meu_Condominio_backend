package com.api.app.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        System.out.println("URI solicitada: " + uri);

        // Ignora as rotas de login e cadastro
        if (uri.startsWith("/usuario/login") ||
                uri.startsWith("/usuario/cadastrar") ||
                uri.startsWith("/auth/login-morador") ||
                uri.startsWith("/usuario/login-proprietario")) {
            System.out.println("Rota pública, ignorando token.");
            filterChain.doFilter(request, response);  // Ignora a validação do token JWT
            return;
        }

        // Extrai token do header
        String bearer = request.getHeader("Authorization");
        String token = (bearer != null && bearer.startsWith("Bearer "))
                ? bearer.substring(7)
                : null;
        System.out.println("Token recebido: " + token);

        if (token != null) {
            try {
                // Verifica se o token é válido
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractUsername(token);
                    String role = jwtUtil.extractRole(token);
                    System.out.println("Usuário: " + username + " | Role: " + role);

                    // Monta a autenticação para o Spring Security
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority(role)));

                    // Seta no contexto de segurança
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("Authentication setado no SecurityContext");
                }
            } catch (JwtException ex) {
                // Caso o token seja inválido ou expirado, o Spring Security lidará com isso
                System.out.println("JWTException: " + ex.getMessage());
                SecurityContextHolder.clearContext();  // Limpa o contexto de segurança
            }
        }

        // Prossegue para o controller (e para o AuthorizationFilter do Spring Security)
        filterChain.doFilter(request, response);
    }

}
