package com.mercadolivro.service

import com.mercadolivro.model.Book
import com.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun create(book: Book) {
        bookRepository.save(book)
    }
}
