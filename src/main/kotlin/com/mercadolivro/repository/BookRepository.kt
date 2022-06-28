package com.mercadolivro.repository

import com.mercadolivro.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<Book, Long>{

}