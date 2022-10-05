package com.example.projectchantadmin.entities

class User(nombre: String, apellido: String, edad: Int, rol: String) {

    private var nombre: String
    private var apellido: String
    private var edad: Int
    private var rol: String

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