package com.facture.proyecto_factura.repository

import com.facture.proyecto_factura.model.ClientModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<ClientModel, Long?> {

    fun findById (id: Long?): ClientModel?
    //Peticion put
    //clase repository
}