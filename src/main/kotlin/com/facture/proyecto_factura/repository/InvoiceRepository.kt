package com.facture.proyecto_factura.repository

import com.facture.proyecto_factura.model.InvoiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<InvoiceModel, Long?> {

    fun findById (id: Long?): InvoiceModel?
    //Peticion put
    //clase repository

    @Query(nativeQuery = true) // Definir el SQL nativo - Ejemplo Factura
    fun filterTotal (value: Double): List <InvoiceModel>
}