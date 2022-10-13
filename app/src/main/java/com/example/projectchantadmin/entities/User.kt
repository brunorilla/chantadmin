package com.example.projectchantadmin.entities

class User(name: String, lastName: String, age: Int, phone: String) {

    var name: String
    var lastName: String
    var age: Int
    var rol: String = "customer"
    var phone: String

    companion object {
        val customer: String = "customer"
        val admin: String = "admin"
    }

    init {
        this.name = name
        this.lastName = lastName
        this.age = age
        this.phone = phone
        this.rol = User.customer
    }

}