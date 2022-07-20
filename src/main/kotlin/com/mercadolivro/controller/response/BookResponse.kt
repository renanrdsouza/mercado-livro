package com.mercadolivro.controller.response

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.Customer
import java.math.BigDecimal

data class BookResponse(

    var name: String,
    var price: BigDecimal,
    var customer: Customer? = null,
    var status: BookStatus?
)
