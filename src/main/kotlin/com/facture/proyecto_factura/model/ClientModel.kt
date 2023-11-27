package com.facture.proyecto_factura.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "client")
class ClientModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "nui_client", unique = true) // Agregado unique = true
    var ciClient: String? = null   // Campo cédula de la tabla cliente

    @Column(name = "full_name_client")
    var fullNameClient: String? = null   // Nombre completo del cliente

    @Column(name = "address_client")
    var addressClient: String? = null   // Dirección del cliente

}