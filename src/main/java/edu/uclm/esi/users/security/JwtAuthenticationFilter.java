package edu.uclm.esi.users.security;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	String requestURI = request.getRequestURI();
    	logger.info("Request URI: " + requestURI); // Log para verificar la URI de la solicitud

        // Permitir rutas públicas sin autenticación
        if (requestURI.startsWith("/pwd/reset") || requestURI.startsWith("/reset-password")) {
            logger.info("Permitiendo acceso sin autenticación para: " + requestURI);
            chain.doFilter(request, response);
            return;
        }
        String token = getJwtFromRequest(request);
        logger.info("Token found: " + token);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // Obtener username y rol del token
            String username = jwtTokenProvider.getUsernameFromToken(token);
            String role = jwtTokenProvider.getRoleFromToken(token);

            logger.info("Extracted username: " + username);
            logger.info("Extracted role: " + role);

            // Crear el objeto de autenticación
            UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(username, null, List.of(() -> "ROLE_" + role));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Establecer autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.warn("No valid token found or token validation failed.");
        }

        chain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        String tokenParam = request.getParameter("token");
        if (StringUtils.hasText(tokenParam)) {
            return tokenParam;
        }
        return null;
    }

}

