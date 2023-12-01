package com.facture.proyecto_factura.controller

import com.facture.proyecto_factura.model.ClientModel
import com.facture.proyecto_factura.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
//import java.awt.print.Pageable
import org.springframework.data.domain.Pageable;

data class JSendResponse<T>(
    val status: String,
    val data: T? = null,
    val message: String? = null
)

@RestController
@RequestMapping("/api/clients") // Cambiado el endpoint
@CrossOrigin(methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE])
class ClientController {
    @Autowired
    lateinit var clientService: ClientService

    /*@GetMapping
    fun list(): ResponseEntity<JSendResponse<List<ClientModel>>> {
        val clientsList = clientService.list()
        return ResponseEntity.ok(JSendResponse("success", clientsList))
    }*/
    @GetMapping// Para paginación en el front
    fun list (model:ClientModel, pageable: Pageable):ResponseEntity<*>{
        val response= clientService.list(pageable,model)
        return ResponseEntity(response, HttpStatus.OK)
    } // Para paginación en el front

    // Peticiones POST - Clase controller
    @PostMapping
    fun save(@RequestBody clientModel: ClientModel): ResponseEntity<JSendResponse<ClientModel>> {
        val savedClient = clientService.save(clientModel)
        return ResponseEntity.status(HttpStatus.CREATED).body(JSendResponse("success", savedClient))
    }

    // Clase controller - Petición Put
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody clientModel: ClientModel): ResponseEntity<JSendResponse<ClientModel>> {
        val updatedClient = clientService.update(id, clientModel)
        return ResponseEntity.ok(JSendResponse("success", updatedClient))
    }

    // Clase controller - Petición Patch
    @PatchMapping("/{id}")
    fun updateName(@PathVariable id: Long, @RequestBody fullNameClient: String): ResponseEntity<JSendResponse<ClientModel>> {
        val updatedClient = clientService.updateName(id, fullNameClient)
        return ResponseEntity.ok(JSendResponse("success", updatedClient))
    }

    // Clase controller - Petición Delete
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<JSendResponse<String>> {
        val deleted = clientService.delete(id)
        return if (deleted) {
            ResponseEntity.ok(JSendResponse("success", "Client deleted successfully"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSendResponse("fail", null, "Client not found"))
        }
    }

    // GET BY ID Clase Controller
    @GetMapping("/{id}")
    fun listById(@PathVariable id: Long): ResponseEntity<JSendResponse<ClientModel>> {
        val client = clientService.listById(id)
        return if (client != null) {
            ResponseEntity.ok(JSendResponse("success", client))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSendResponse("fail", null, "Client not found"))
        }
    }
}
