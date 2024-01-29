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
    lateinit var clientService: ClientService // Para injectar la clase que vamos a probar en la sección de pruebas

    @Mock
    lateinit var clientRepository: ClientRepository // Se injecta un objeto que va simular el repositorio

    private val clientMock = ClientModel().apply {
        id=1
        ciClient="0301707030"
        fullNameClient="Juan"
        addressClient= "Cuenca"
    }

    @Test
    fun saveClientCorrect(){ // Puedo probar únicamente un test
        Mockito.`when`(clientRepository.save(Mockito.any(ClientModel::class.java))).thenReturn(clientMock) // Aquí estoy
        // simulando lo que se tiene que devolver a la clase, en este caso un cliente simulado
        val response = clientService.save(clientMock) // Aquí estamos probando el método save cuando sea correcto
        Assertions.assertEquals(response.id, clientMock.id) // Comparamos que el objeto eniado sea el mismo que el de clientMock
    }


    @Test // Testeo de en caso que no sea igual
    fun saveClientWhenFullnameIsBlank(){
        Assertions.assertThrows(Exception::class.java) { // cada que se genere un error vamos a capturar la 'Exception'
            clientMock.apply { fullNameClient=" "}
            Mockito.`when`(clientRepository.save(Mockito.any(ClientModel::class.java))).thenReturn(clientMock)
            clientService.save(clientMock)
        }
    }

    @Test
    fun saveClientWhenAddressIsBlank(){
        Assertions.assertThrows(Exception::class.java) { // cada que se genere un error vamos a capturar la 'Exception'
            clientMock.apply { addressClient=" "}
            Mockito.`when`(clientRepository.save(Mockito.any(ClientModel::class.java))).thenReturn(clientMock)
            clientService.save(clientMock)
        }
    }
}