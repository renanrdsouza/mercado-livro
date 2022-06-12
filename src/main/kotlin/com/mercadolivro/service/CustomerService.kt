package com.mercadolivro.service

import com.mercadolivro.dto.CustomerDto
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.exception.NotFoundException

class CustomerService(
    private val repository: CustomerRepository,
    private val notFoundMessage: String = "Customer not found"
) {

    fun listAll(): List<CustomerDto> {
        return repository.findAll().map { customer ->
            CustomerDto(customer.name, customer.email)
        }.toList()
    }

    fun findBy(id: Long): CustomerDto {
        val customer = repository.findById(id).get()
        return CustomerDto(customer.name, customer.email)
    }

    fun insert(customerDto: CustomerDto) {
        repository.save(Customer(null, customerDto.name, customerDto.email))
    }

    fun update(id: Long, customerDto: CustomerDto): CustomerDto {
        val customer = repository.findById(id)
            .orElseThrow{NotFoundException(notFoundMessage)}

        customer.name = customerDto.name
        customer.email = customerDto.email
        repository.save(customer)

        return CustomerDto(customer.name, customer.email)
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }
}