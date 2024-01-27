package br.com.todolistAPI.config.security;

import br.com.todolistAPI.repositories.AuthenticationRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Classe que define filtros que podem ser utilizados no filterChain (cadeia de filtros)
 * para validações no Spring Security
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationRepository authenticationRepository;

    /**
     * Método para manipular o fluxo da cadeia de filtros (filterChain). <br>
     * Recupera o token: <br>
     * - Se não for nulo, valida o token e salva no contexto de validação <br>
     * - Se for nulo, simplemente devolve o fluxo da cadeia de filtros sem alteração
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);
        if (token != null){
            var username = tokenService.validateToken(token);
            UserDetails user = authenticationRepository.findByUsername(username);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Método para recuperar o token vindo da requisição HTTP. É retirado o "Bearer" para limpar
     * a requisição e retornar apenas o token
     * @param request
     * @return token ou null se o token não encontrado
     */
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
