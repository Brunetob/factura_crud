package com.facture.proyecto_factura.service

import com.facture.proyecto_factura.model.ClientModel
import com.facture.proyecto_factura.model.InvoiceModel
import com.facture.proyecto_factura.repository.ClientRepository
import com.facture.proyecto_factura.repository.InvoiceRepository
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.io.File

class InvoiceServiceTest { // Validar lalves foráneas

    @InjectMocks
    lateinit var invoiceService: InvoiceService

    @Mock
    lateinit var invoiceRepository: InvoiceRepository

    @Mock
    lateinit var clientRepository: ClientRepository

    val clientMock = ClientModel().apply {
        id=1
        ciClient="0301707030"
        fullNameClient="Juan"
        addressClient= "Cuenca"
    }

    val jsonString = File("./src/test/resources/invoice.json").readText(Charsets.UTF_8)
    val invoiceMock = Gson().fromJson(jsonString, InvoiceModel::class.java)

    @Test
    fun saveInvoiceWhenIsCorrect(){
        Mockito.`when`(clientRepository.findById(clientMock.id)).thenReturn(clientMock)
        Mockito.`when`(invoiceRepository.save(Mockito.any(InvoiceModel::class.java))).thenReturn(invoiceMock)
        val response = invoiceService.save(invoiceMock)
        Assertions.assertEquals(response.id, invoiceMock.id)
    }
}