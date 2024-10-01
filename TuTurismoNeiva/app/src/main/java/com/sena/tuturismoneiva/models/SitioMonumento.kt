package com.sena.tuturismoneiva.models

import java.sql.Date
import java.sql.Time

data class SitioMonumento(
    val idSitioMonumento: String,
    val clasificacionSitioMonumento: Char,
    val nombreSitioMonumento:String,
    val ubicacionSitioMonumento:String,
    val calificacionSitioMonumento:String,
    val direccionSitioMonumento: String,
    val descripcionSitioMonumento: String,
    val detalladaSitioMonumento:String,
    val horarioSitioMonumento:Time,
    val fechaCreacionSitioMonumento:Date,
    val fechaModificacionSitioMonumento:Date,
    val idAutor:String,
    val contactoSitioMonumento:String,
    val imagen:String,
)
