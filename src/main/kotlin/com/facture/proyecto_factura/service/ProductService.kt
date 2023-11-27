package com.facture.proyecto_factura.service

import com.facture.proyecto_factura.model.ProductModel
import com.facture.proyecto_factura.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductService {
    @Autowired
    lateinit var productRepository: ProductRepository

    fun list(): List<ProductModel> {
        return productRepository.findAll()
    }

    fun save(productModel: ProductModel): ProductModel {
        validateProductModel(productModel)
        return productRepository.save(productModel)
    }

    fun update(id: Long, productModel: ProductModel): ProductModel {
        validateProductModel(productModel)

        if (!productRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Producto con ID $id no encontrado")
        }

        productModel.id = id
        return productRepository.save(productModel)
    }

    fun delete(id: Long): Boolean {
        if (!productRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Producto con ID $id no encontrado")
        }

        productRepository.deleteById(id)
        return true
    }

    fun listById(id: Long): ProductModel? {
        return productRepository.findById(id).orElse(null)
    }

    private fun validateProductModel(productModel: ProductModel) {
        productModel.apply {
            description?.takeIf { it.trim().isNotEmpty() }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "La descripción del producto no debe ser nula o vacía")

            brand?.takeIf { it.trim().isNotEmpty() }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "La marca del producto no debe ser nula o vacía")

            price?.takeIf { it >= 0 }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio del producto no debe ser negativo")

            stock?.takeIf { it >= 0 }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock del producto no debe ser negativo")
        }
    }
}
