package com.mercadolivro.controller

import com.mercadolivro.controller.request.toBook
import com.mercadolivro.controller.request.toCustomer
import com.mercadolivro.dto.BookDto
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.Book
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

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

    @GetMapping
    fun findAll(): ResponseEntity<List<Book>> = ResponseEntity.ok().body(bookService.findAll())

    @GetMapping("/active")
    fun getActives(): ResponseEntity<List<Book>> = ResponseEntity.ok().body(bookService.findByStatus())

    @GetMapping("/{id}")
    fun findBy(@RequestParam id: Long): ResponseEntity<Book> = ResponseEntity.ok().body(bookService.findBy(id))

    @DeleteMapping("/{id}")
    fun deleteBy(@RequestParam id: Long): ResponseEntity<Any> {
        bookService.deleteBy(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun update(
        @RequestParam id: Long,
        @RequestBody book: BookDto
    ): ResponseEntity<Book> {
        return ResponseEntity.ok().body(bookService.update(id, book))
    }
}