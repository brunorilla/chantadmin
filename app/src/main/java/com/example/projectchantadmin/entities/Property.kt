package com.example.projectchantadmin.entities

class Property(
    name: String,
    apartmentList: Array<Apartment>,
    address: String,
) {

    var name: String
    var apartmentList: Array<Apartment>
    var address: String
    var security: Boolean = false

    init {
        this.name = name
        this.apartmentList = apartmentList
        this.address = address
        this.security = security
    }

    fun changeSecurity() {
        this.security = true
    }

}