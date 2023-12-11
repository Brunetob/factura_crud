package com.facture.proyecto_factura.controller

import com.facture.proyecto_factura.dto.ProductDto
import com.facture.proyecto_factura.model.ProductModel
import com.facture.proyecto_factura.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
@CrossOrigin(methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE])
class ProductController {
    @Autowired
    lateinit var productService: ProductService

    /*@GetMapping
    fun list(): ResponseEntity<JSendResponse<List<ProductModel>>> {
        val productsList = productService.list()
        return ResponseEntity.ok(JSendResponse("success", productsList))
    }*/

    @GetMapping
    fun list (model:ProductModel, pageable: Pageable):ResponseEntity<*>{
        val response= productService.list(pageable,model)
        return ResponseEntity(response, HttpStatus.OK)
    }// Para paginación en el front

    @PostMapping
    fun save(@RequestBody productModel: ProductModel): ResponseEntity<JSendResponse<ProductModel>> {
        val savedProduct = productService.save(productModel)
        return ResponseEntity.status(HttpStatus.CREATED).body(JSendResponse("success", savedProduct))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody productModel: ProductModel): ResponseEntity<JSendResponse<ProductModel>> {
        val updatedProduct = productService.update(id, productModel)
        return ResponseEntity.ok(JSendResponse("success", updatedProduct))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<JSendResponse<String>> {
        val deleted = productService.delete(id)
        return if (deleted) {
            ResponseEntity.ok(JSendResponse("success", "Product deleted successfully"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSendResponse("fail", null, "Product not found"))
        }
    }

    @GetMapping("/{id}")
    fun listById(@PathVariable id: Long): ResponseEntity<JSendResponse<ProductModel>> {
        val product = productService.listById(id)
        return if (product != null) {
            ResponseEntity.ok(JSendResponse("success", product))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSendResponse("fail", null, "Product not found"))
        }
    }

    /*******************************Espacio para native query******************************/

    /*******************************Código para Mostrar Lista Dto******************************/
    // Endpoint para obtener una lista de DTOs
    @GetMapping("/list-dto")
    fun listDto(): ResponseEntity<JSendResponse<List<ProductDto>>> {
        val productDtoList = productService.listDto()
        return ResponseEntity.ok(JSendResponse("success", productDtoList))
    }
}
