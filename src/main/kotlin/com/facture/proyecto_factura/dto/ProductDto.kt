package com.facture.proyecto_factura.dto

// DTO para devolver descripción y marca
// id = 1 | brand = toyota
// Por lo general los Dto se inicializan con una variable
class ProductDto ( //Clase POJO
    var id: Long?,
    var descriptionBrand: String?
)