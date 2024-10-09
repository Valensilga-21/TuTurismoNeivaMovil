package com.sena.tuturismoneiva.models

data class CambioContrasenaRequest(
    val antiguaContrasena: String,
    val nuevaContrasena: String,
    val confirmarContrasena: String
)
