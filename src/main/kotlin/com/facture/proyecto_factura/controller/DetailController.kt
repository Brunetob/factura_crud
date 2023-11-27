package com.facture.proyecto_factura.controller

import com.facture.proyecto_factura.model.DetailModel
import com.facture.proyecto_factura.service.DetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/details")
@CrossOrigin(methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE])
class DetailController {
    @Autowired
    lateinit var detailService: DetailService

    @GetMapping
    fun list(): ResponseEntity<JSendResponse<List<DetailModel>>> {
        val detailsList = detailService.list()
        return ResponseEntity.ok(JSendResponse("success", detailsList))
    }

    @PostMapping
    fun save(@RequestBody detailModel: DetailModel): ResponseEntity<JSendResponse<DetailModel>> {
        val savedDetail = detailService.save(detailModel)
        return ResponseEntity.status(HttpStatus.CREATED).body(JSendResponse("success", savedDetail))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody detailModel: DetailModel): ResponseEntity<JSendResponse<DetailModel>> {
        val updatedDetail = detailService.update(id, detailModel)
        return ResponseEntity.ok(JSendResponse("success", updatedDetail))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<JSendResponse<String>> {
        val deleted = detailService.delete(id)
        return if (deleted) {
            ResponseEntity.ok(JSendResponse("success", "Detail deleted successfully"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSendResponse("fail", null, "Detail not found"))
        }
    }

    @GetMapping("/{id}")
    fun listById(@PathVariable id: Long): ResponseEntity<JSendResponse<DetailModel>> {
        val detail = detailService.listById(id)
        return if (detail != null) {
            ResponseEntity.ok(JSendResponse("success", detail))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSendResponse("fail", null, "Detail not found"))
        }
    }
}
