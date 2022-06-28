package com.mercadolivro.dto

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class BookDto(

    var name: String,

    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Long
)
