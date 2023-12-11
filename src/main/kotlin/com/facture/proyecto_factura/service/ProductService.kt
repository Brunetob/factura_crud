package com.facture.proyecto_factura.service

import com.facture.proyecto_factura.dto.ProductDto
import com.facture.proyecto_factura.mapper.ProductMapper
import com.facture.proyecto_factura.model.ProductModel
import com.facture.proyecto_factura.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProductService {
    @Autowired
    lateinit var productRepository: ProductRepository

    /*fun list(): List<ProductModel> {
        return productRepository.findAll()
    }*/
    fun list (pageable: Pageable,model:ProductModel):Page<ProductModel>{
        val matcher = ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withMatcher(("description"), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        return productRepository.findAll(Example.of(model, matcher), pageable)
    }// Código para paginación

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

    /************************Espacio para colocar native query*****************/

    /************************Incio del Product DTO*****************/
    fun listDto(): List<ProductDto> {
        val productList = productRepository.findAll() // conjunto de productos
        //necesitamos devolver un conjunto de productosDTo
        val productDtoList = mutableListOf<ProductDto>() // lista mutable vacía

        for (product in productList) { // iterar en la lista
            val dto = ProductMapper.mapToDto(product) // se llama al mapper
            productDtoList.add(dto) // se le agrega a la lista de productos DTO
        }

        return productDtoList // Se retorna la lista
    }
}
