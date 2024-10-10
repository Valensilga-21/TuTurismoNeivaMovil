package com.sena.tuturismoneiva.config

class config {
    companion object{
        //http://10.192.80.141:8080/api/v1/Usuario/
        var urlBase = "http://10.192.66.60:8082/api/v1/"
        var urlUsuario = urlBase + "publico/Usuario/"
        var urlProfile= urlBase + "usuario/"
        var urlEditarProfile= urlBase + "usuario/"
        var urlSitios = urlBase + "SitioMonumento/" 
        var urlCambiarContra = urlBase + "usuario/cambiarContrasena/"
    }
}