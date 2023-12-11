package com.facture.proyecto_factura.repository

import com.facture.proyecto_factura.model.ProductModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductModel, Long?> {

    fun findById (id: Long?): ProductModel?
    //Peticion put
    //clase repository

    /*Native Query del Producto*/
    @Query(nativeQuery = true, name = "Product.findTopSelling")
    fun findTopSellingProducts(): List<ProductModel>
}