package com.facture.proyecto_factura.service

import com.facture.proyecto_factura.model.ClientModel
import com.facture.proyecto_factura.repository.ClientRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

class ClientServiceTest { // clase de prueba

    @InjectMocks
    lateinit var clientService: ClientService

    @Mock
    lateinit var clientRepository: ClientRepository

    private val clientMock = ClientModel().apply {
        id=1
        ciClient="0301707030"
        fullNameClient="Juan"
        addressClient= "Cuenca"
    }

    @Test
    fun saveClientCorrect(){ // Puedo probar únicamente un test
        Mockito.`when`(clientRepository.save(Mockito.any(ClientModel::class.java))).thenReturn(clientMock)
        val response = clientService.save(clientMock)
        Assertions.assertEquals(response.id, clientMock.id)
    }


    @Test
    fun saveClientWhenFullnameIsBlank(){

        Assertions.assertThrows(Exception::class.java) {
            clientMock.apply { fullNameClient=" "}
            Mockito.`when`(clientRepository.save(Mockito.any(ClientModel::class.java))).thenReturn(clientMock)
            clientService.save(clientMock)
        }

    }
    /*@Test
    fun list() {

    }

    @Test
    fun save() {
    }*/
}