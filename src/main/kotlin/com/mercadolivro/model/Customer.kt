package com.mercadolivro.model

import javax.persistence.Entity

@Entity
data class Customer(

    val id: Long?,
    var name: String,
    var email: String
)