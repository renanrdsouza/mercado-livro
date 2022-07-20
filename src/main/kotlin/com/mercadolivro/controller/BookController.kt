package com.mercadolivro.controller

import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.request.toBook
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.dto.BookDto
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun findAll(@PageableDefault(page = 0, size = 10) page: Pageable): ResponseEntity<Page<BookResponse>> {

        return ResponseEntity.ok().body(bookService.findAll(page))
    }

    @GetMapping("/active")
    fun getActives(@PageableDefault(page = 0, size = 2) page: Pageable): ResponseEntity<Page<BookResponse>> =
        ResponseEntity.ok().body(bookService.findByStatus(page))

    @GetMapping("/{id}")
    fun findBy(@PathVariable id: Long): ResponseEntity<BookResponse> = ResponseEntity.ok().body(bookService.findBy(id))

    @DeleteMapping("/{id}")
    fun deleteBy(@PathVariable id: Long): ResponseEntity<Any> {

        bookService.deleteBy(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody book: PutBookRequest
    ): ResponseEntity<BookResponse> {

        return ResponseEntity.ok().body(bookService.update(id, book))
    }
}