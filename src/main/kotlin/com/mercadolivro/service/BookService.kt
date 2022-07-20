package com.mercadolivro.service

import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.request.toResponse
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.Book
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository,
) {

    fun create(book: Book) {
        bookRepository.save(book)
    }

    fun findAll(page: Pageable): Page<BookResponse> {
        return bookRepository.findAll(page).map { book ->
            BookResponse(book.name, book.price, book.customer, book.status)
        }
    }

    fun findByStatus(page: Pageable): Page<BookResponse> {
        return bookRepository.findByStatus(BookStatus.ATIVO, page).map {book ->
            BookResponse(book.name, book.price, book.customer, book.status)
        }
    }

    fun findBy(id: Long): BookResponse {
        val book = bookRepository.findById(id).orElseThrow {NotFoundException(Errors.ML1001.message.format(id), Errors.ML1001.code)}
        return BookResponse(book.name, book.price, book.customer, book.status)
    }

    fun deleteBy(id: Long) {
        val book = bookRepository.findById(id).orElseThrow { NotFoundException(Errors.ML1001.message.format(id), Errors.ML1001.code) }
        book.status = BookStatus.CANCELADO

        bookRepository.save(book)
    }

    fun update(id: Long, bookSaved: PutBookRequest): BookResponse {
        val bookToUpdate = bookRepository.findById(id).orElseThrow { NotFoundException(Errors.ML1001.message.format(id), Errors.ML1001.code) }
        bookToUpdate.name = bookSaved.name!!
        bookToUpdate.price = bookSaved.price!!
        return bookRepository.save(bookToUpdate).toResponse()
    }

    fun deleteByCustomer(customer: Customer) {
        val books = bookRepository.findByCustomer(customer)
        books
            .forEach { it.status = BookStatus.DELETADO }
            .also { bookRepository.saveAll(books) }
    }
}
