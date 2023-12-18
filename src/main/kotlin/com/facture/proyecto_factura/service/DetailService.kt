package com.facture.proyecto_factura.service

import com.facture.proyecto_factura.model.DetailModel
import com.facture.proyecto_factura.repository.DetailRepository
import com.facture.proyecto_factura.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class DetailService {
    @Autowired
    lateinit var detailRepository: DetailRepository

    //Autowired añadidos par la lógica de restar stock
    @Autowired
    lateinit var productRepository: ProductRepository

    fun list(): List<DetailModel> {
        return detailRepository.findAll()
    }
    fun save(detailModel: DetailModel): DetailModel {
        validateDetailModel(detailModel)

        // Guardar el detalle en el repositorio
        val savedDetail = detailRepository.save(detailModel)

        // Obtener el producto asociado al detalle
        val productID = detailModel.product?.id ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Detalle sin producto asociado")
        val product = productRepository.findById(productID)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Producto con ID $productID no encontrado") }

        // Actualizar el stock del producto
        product.apply {
            //stock -= detailModel.quantity!!
            stock = stock?.minus(detailModel.quantity ?: 0) ?: 0
        }

        // Guardar el producto actualizado en el repositorio
        productRepository.save(product)

        return savedDetail
    }

    fun update(id: Long, detailModel: DetailModel): DetailModel {
        validateDetailModel(detailModel)

        if (!detailRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle con ID $id no encontrado")
        }

        detailModel.id = id
        return detailRepository.save(detailModel) // Código normal
    }

    fun delete(id: Long): Boolean {
        if (!detailRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle con ID $id no encontrado")
        }

        detailRepository.deleteById(id)
        return true // Código normal
    }

    fun listById(id: Long): DetailModel? {
        return detailRepository.findById(id).orElse(null)
    }

    private fun validateDetailModel(detailModel: DetailModel) {
        detailModel.apply {
            quantity?.takeIf { it > 0 }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor que cero")

            price?.takeIf { it >= 0 }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio no debe ser negativo")

            invoice?.takeIf { it.id != null }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Se requiere una factura válida para el detalle")

            product?.takeIf { it.id != null }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Se requiere un producto válido para el detalle")
        }
    }
}
