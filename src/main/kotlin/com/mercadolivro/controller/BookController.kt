package com.mercadolivro.controller

import com.mercadolivro.controller.request.toBook
import com.mercadolivro.controller.request.toCustomer
import com.mercadolivro.dto.BookDto
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(
    val customerService: CustomerService,
    val bookService: BookService
) {

    @PostMapping
    fun create(@RequestBody book: BookDto) {
        val customer = customerService.findCustomer(book.customerId)
        bookService.create(book.toBook(customer))
    }
}