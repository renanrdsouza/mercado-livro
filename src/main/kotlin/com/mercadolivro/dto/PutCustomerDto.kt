package com.mercadolivro.dto

import com.mercadolivro.enums.CustomerStatus

data class PutCustomerDto(
    val name: String,
    val email: String,
    val status: CustomerStatus
)
