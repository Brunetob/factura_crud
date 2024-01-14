package com.facture.proyecto_factura.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.facture.proyecto_factura.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit

@Component
class JwtUtil {
    @Autowired
    lateinit var userRepository: UserRepository
    private val SECRET_KEY = "klav3_s3cr374"
    private val ALGORITHM: Algorithm = Algorithm.HMAC256(SECRET_KEY)

    fun create(username: String?): String? {
        val userEntity = userRepository.findByUsername(username!!)
        val roles: Array<String?> = userEntity?.roles?.map {
                role -> role.role }!!.toTypedArray()
        return JWT.create()
            .withArrayClaim("roles", roles)
            .withSubject(username)
            .withIssuer("app-admin")
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
            .sign(ALGORITHM)
    }
    fun isValid(jwt: String?): Boolean {
        return try {
            JWT.require(ALGORITHM)
                .build()
                .verify(jwt)
            true
        } catch (e: JWTVerificationException) {
            false
        }
    }
    fun getUsername(jwt: String?): String? {
        return JWT.require(ALGORITHM)
            .build()
            .verify(jwt)
            .subject
    }
}