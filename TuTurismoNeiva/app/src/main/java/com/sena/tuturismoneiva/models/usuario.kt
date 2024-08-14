package com.sena.tuturismoneiva.models

data class usuario(
    var idUsuario: String,
    var nombreCompleto: String,
    var correoElectronico: String,
    var telefono: Int,
    var contra: String,
    var coContra: String
)
