package com.facture.proyecto_factura.model

import jakarta.persistence.*

@Entity
@Table(name = "detail")
class DetailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "quantity")
    var quantity: Int? = null

    @Column(name = "price")
    var price: Float? = null

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    var invoice: InvoiceModel? = null

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    var product: ProductModel? = null
}
