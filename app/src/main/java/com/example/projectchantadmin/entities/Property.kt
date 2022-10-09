package com.example.projectchantadmin.entities

class Property(
    departmentList: Array<Department>,
    address: String,
    security: Boolean
) {

    var departmentList: Array<Department>
    lateinit var address: String
    var security: Boolean = false

    init {
        this.departmentList = departmentList
        this.address = address
        //this.security = security
    }

    fun changeSecurity() {
        this.security = true
    }

}