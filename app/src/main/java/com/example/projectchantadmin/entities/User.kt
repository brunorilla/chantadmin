package com.example.projectchantadmin.entities

class User(nombre: String, apellido: String, edad: Int, rol: String) {

    var nombre: String
    var apellido: String
    var edad: Int
    var rol: String

    companion object {
        val customer: String = "customer"
        val admin: String = "admin"
    }

    init {
        this.nombre = nombre
        this.apellido = apellido
        this.edad = edad
        this.rol = rol
    }

}