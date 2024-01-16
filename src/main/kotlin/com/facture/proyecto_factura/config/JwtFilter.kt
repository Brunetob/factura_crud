package com.facture.proyecto_factura.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter: OncePerRequestFilter() {
    @Autowired
    private val jwtUtil: JwtUtil? = null

    @Autowired
    private val userDetailsService: UserDetailsService? = null

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 1. Validar que sea un Header Authorization valido
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response)
            return
        }

        // 2. Validar que el JWT sea valido
        val jwt = authHeader.split(" ".toRegex()).dropLastWhile{ it.isEmpty()}.toTypedArray()[1].trim{ it<= ' '}
        if (!jwtUtil!!.isValid(jwt)) {
            filterChain.doFilter(request, response)
            return
        }

        // 3. Cargar el usuario del UserDetailsService
        val username = jwtUtil.getUsername(jwt)
        val user: User = userDetailsService!!.loadUserByUsername(username) as User

        // 4. Cargar al usuario en el contexto de seguridad.
        val authenticationToken = UsernamePasswordAuthenticationToken(
            user.username, user.password, user.authorities
        )
        authenticationToken.details= WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication= authenticationToken
        filterChain.doFilter(request, response)
    }


}