package com.facture.proyecto_factura.repository

import com.facture.proyecto_factura.model.DetailModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DetailRepository : JpaRepository<DetailModel, Long?> {

    fun findById(id: Long?): DetailModel?
    // Puedes agregar otros métodos específicos si es necesario
}
