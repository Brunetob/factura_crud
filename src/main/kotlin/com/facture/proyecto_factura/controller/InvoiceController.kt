package com.facture.proyecto_factura.controller

import com.facture.proyecto_factura.model.InvoiceModel
import com.facture.proyecto_factura.service.InvoiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE])
class InvoiceController {
    @Autowired
    lateinit var invoiceService: InvoiceService

    @GetMapping
    fun list(): ResponseEntity<JSendResponse<List<InvoiceModel>>> {
        val invoicesList = invoiceService.list()
        return ResponseEntity.ok(JSendResponse("success", invoicesList))
    }

    @PostMapping
    fun save(@RequestBody invoiceModel: InvoiceModel): ResponseEntity<JSendResponse<InvoiceModel>> {
        val savedInvoice = invoiceService.save(invoiceModel)
        return ResponseEntity.status(HttpStatus.CREATED).body(JSendResponse("success", savedInvoice))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody invoiceModel: InvoiceModel): ResponseEntity<JSendResponse<InvoiceModel>> {
        val updatedInvoice = invoiceService.update(id, invoiceModel)
        return ResponseEntity.ok(JSendResponse("success", updatedInvoice))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<JSendResponse<String>> {
        val deleted = invoiceService.delete(id)
        return if (deleted) {
            ResponseEntity.ok(JSendResponse("success", "Invoice deleted successfully"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSendResponse("fail", null, "Invoice not found"))
        }
    }

    @GetMapping("/{id}")
    fun listById(@PathVariable id: Long): ResponseEntity<JSendResponse<InvoiceModel>> {
        val invoice = invoiceService.listById(id)
        return if (invoice != null) {
            ResponseEntity.ok(JSendResponse("success", invoice))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSendResponse("fail", null, "Invoice not found"))
        }
    }

    // Definir el SQL nativo - Ejemplo Factura
    /*@GetMapping("/filter-total/{value}")//endpoint de sub-ruta --> localhost:8081/invoice/totals/55) // Definir el SQL nativo - Ejemplo Factura
    fun listTotals (@PathVariable("value") value: Double ):ResponseEntity<*>{
        return ResponseEntity(invoiceService.listByTotal(value), HttpStatus.OK) //Se coloca la función que se creó en el service (listByTotal)
    }*/
    @GetMapping("/filter-total/{value}")
    fun listTotals (@PathVariable("value") value: Double ):ResponseEntity<*>{
        return ResponseEntity(invoiceService.filterTotal(value), HttpStatus.OK)
    }
}
