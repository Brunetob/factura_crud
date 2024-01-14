package com.facture.proyecto_factura.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    var id: Long? = null

    var username: String? = null

    @Column(nullable = false, length = 200)
    var password: String? = null

    @Column(length = 50)
    var email: String? = null

    @Column(nullable = false)
    var locked: Boolean? = null
    var disabled: Boolean? = null
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    var roles: List<RoleEntity>? = null
}