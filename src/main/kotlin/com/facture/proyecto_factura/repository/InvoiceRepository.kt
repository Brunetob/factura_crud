package com.facture.proyecto_factura.repository

import com.facture.proyecto_factura.model.InvoiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<InvoiceModel, Long?> {

    fun findById (id: Long?): InvoiceModel?
    //Peticion put
    //clase repository
}