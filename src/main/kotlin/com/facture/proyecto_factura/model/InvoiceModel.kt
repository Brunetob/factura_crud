package com.facture.proyecto_factura.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "invoice")
class InvoiceModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    /*
    * La anotación @ManyToOne junto con @JoinColumn se utiliza para establecer la relación many-to-one con la entidad ClientModel. Esto representa la relación entre client_id en la tabla invoice y la columna id en la tabla client.
    */

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    var clientId: ClientModel? = null

    @Column(name = "code", length = 10)
    var code: String? = null

    @Column(name = "create_at", length = 52)
    var createAt: String? = null

    @Column(name = "total")
    var total: Float? = null
}