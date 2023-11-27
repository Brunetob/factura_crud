package com.facture.proyecto_factura.model

import jakarta.persistence.*

@Entity
@Table(name = "product")
class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "description", length = 250)
    var description: String? = null

    @Column(name = "brand", length = 50)
    var brand: String? = null

    @Column(name = "price")
    var price: Float? = null

    @Column(name = "stock")
    var stock: Long? = null
}
