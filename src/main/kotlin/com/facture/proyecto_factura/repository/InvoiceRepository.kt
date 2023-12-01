package com.facture.proyecto_factura.repository

import com.facture.proyecto_factura.model.ClientModel
import com.facture.proyecto_factura.model.InvoiceModel
import com.facture.proyecto_factura.model.ProductModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<InvoiceModel, Long?> {

    fun findById (id: Long?): InvoiceModel?
    //Peticion put
    //clase repository
    @Query(nativeQuery = true, name = "Invoice.filterTotal") // Definir el SQL nativo - Ejemplo Factura
    fun filterTotal(@Param("value") value: Double?): List<InvoiceModel>?
}