package com.mercadolivro.repository

import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {
    fun findByNameContaining(it: String): List<CustomerResponse>
}