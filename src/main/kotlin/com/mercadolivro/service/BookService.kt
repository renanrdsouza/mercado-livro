package com.mercadolivro.service

import com.mercadolivro.dto.BookDto
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.Book
import com.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(
    val bookRepository: BookRepository,
    private val notFoundMessage: String = "Customer not found"
) {

    fun create(book: Book) {
        bookRepository.save(book)
    }

    fun findAll(): List<Book> {
        return bookRepository.findAll()
    }

    fun findByStatus(): List<Book> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    fun findBy(id: Long): Book {
        return bookRepository.findById(id).orElseThrow {NotFoundException(notFoundMessage)}
    }

    fun deleteBy(id: Long) {
        val book = findBy(id)
        book.status = BookStatus.CANCELADO

        bookRepository.save(book)
    }
}
