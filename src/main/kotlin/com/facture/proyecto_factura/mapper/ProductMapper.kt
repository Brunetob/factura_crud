package com.facture.proyecto_factura.mapper

import com.facture.proyecto_factura.dto.ProductDto
import com.facture.proyecto_factura.model.ProductModel

/*class ProductMapper {
}*/

object ProductMapper {
    fun mapToDto(product: ProductModel): ProductDto{
        return ProductDto(
            product.id,
            "${product.description} ${product.brand}"
        )
    }
}