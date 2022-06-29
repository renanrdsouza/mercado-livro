package com.mercadolivro.controller.request

import com.mercadolivro.dto.BookDto
import com.mercadolivro.dto.CustomerDto
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.Book
import com.mercadolivro.model.Customer

fun CustomerDto.toCustomer(): Customer {
    return Customer(
        name = this.name,
        email = this.email)
}

fun BookDto.toBook(customer: Customer): Book {
    return Book(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customer )
}
