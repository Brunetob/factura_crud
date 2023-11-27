package com.facture.proyecto_factura.service

import com.facture.proyecto_factura.model.ClientModel
import com.facture.proyecto_factura.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ClientService {
    @Autowired
    lateinit var clientRepository: ClientRepository

    fun list(): List<ClientModel> {
        return clientRepository.findAll()
    }

    // PETICIONES POST
    // clase service
    fun save(clientModel: ClientModel): ClientModel {
        try {
            validateClientModel(clientModel)
            return clientRepository.save(clientModel)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
        }
    }

    // clase service - Petición put
    fun update(id: Long, clientModel: ClientModel): ClientModel {
        try {
            validateClientModel(clientModel)
            if (!clientRepository.existsById(id)) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "ID no existe")
            }
            clientModel.id = id
            return clientRepository.save(clientModel)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
        }
    }

    // clase service - Peticiones patch
    fun updateName(id: Long, fullNameClient: String): ClientModel {
        try {
            val existingModel = clientRepository.findById(id)
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ID no existe") }

            existingModel.fullNameClient = fullNameClient

            return clientRepository.save(existingModel)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
        }
    }

    // clase service - Delete by id
    fun delete(id: Long): Boolean {
        try {
            if (!clientRepository.existsById(id)) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "ID no existe")
            }
            clientRepository.deleteById(id)
            return true
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
        }
    }

    // GET BY ID clase service
    fun listById(id: Long): ClientModel? {
        return clientRepository.findById(id).orElse(null)
    }

    // Validaciones comunes
    private fun validateClientModel(clientModel: ClientModel) {
        clientModel.apply {
            fullNameClient?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("El nombre del cliente no debe ser nulo o vacío")
            // Otros campos y validaciones según tus necesidades
        }
    }
}