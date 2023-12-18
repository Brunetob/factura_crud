package com.facture.proyecto_factura.service

import com.facture.proyecto_factura.model.InvoiceModel
import com.facture.proyecto_factura.repository.ClientRepository
import com.facture.proyecto_factura.repository.InvoiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class InvoiceService {
    @Autowired
    lateinit var invoiceRepository: InvoiceRepository

    @Autowired
    lateinit var clientRepository: ClientRepository

    fun list(): List<InvoiceModel> {
        return invoiceRepository.findAll()
    }

    fun save(invoiceModel: InvoiceModel): InvoiceModel {
        validateInvoiceModel(invoiceModel)

        // Verificar si el cliente asociado existe
        val clientId = invoiceModel.clientId?.id
        if (clientId != null && !clientRepository.existsById(clientId)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente con ID $clientId no encontrado")
        }

        return invoiceRepository.save(invoiceModel)
    }

    fun update(id: Long, invoiceModel: InvoiceModel): InvoiceModel {
        validateInvoiceModel(invoiceModel)

        if (!invoiceRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Factura con ID $id no encontrada")
        }

        // Verificar si el cliente asociado existe
        val clientId = invoiceModel.clientId?.id //aquí era client
        if (clientId != null && !clientRepository.existsById(clientId)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente con ID $clientId no encontrado")
        }

        invoiceModel.id = id
        return invoiceRepository.save(invoiceModel)
    }

    fun delete(id: Long): Boolean {
        if (!invoiceRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Factura con ID $id no encontrada")
        }

        invoiceRepository.deleteById(id)
        return true
    }

    fun listById(id: Long): InvoiceModel? {
        return invoiceRepository.findById(id).orElse(null)
    }

    private fun validateInvoiceModel(invoiceModel: InvoiceModel) {
        invoiceModel.apply {
            code?.takeIf { it.trim().isNotEmpty() }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El código de la factura no debe ser nulo o vacío")

            createAt?.takeIf { it.trim().isNotEmpty() }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de creación no debe ser nula o vacía")

            total?.takeIf { it >= 0 }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El total de la factura no debe ser negativo")
        }
    }

    fun listByTotal(value: Double): List<InvoiceModel>{ // Definir el SQL nativo - Ejemplo Factura
        return invoiceRepository.findAll()
    }
    fun filterTotal(value:Double?): List<InvoiceModel>? {
        return invoiceRepository.filterTotal(value)
    }
}
