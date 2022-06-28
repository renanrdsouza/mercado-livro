package com.mercadolivro.service

import com.mercadolivro.dto.CustomerDto
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val repository: CustomerRepository,
    private val notFoundMessage: String = "Customer not found"
) {

    fun listAll(name: String?): List<CustomerDto> {
        name?.let {
            return repository.findByNameContaining(it)
        }

        return repository.findAll().map { customer ->
            CustomerDto(customer.name, customer.email)
        }.toList()
    }

    fun findBy(id: Long): CustomerDto {
        val customer = repository.findById(id).get()
        return CustomerDto(customer.name, customer.email)
    }

    fun findCustomer(id: Long) : Customer {
        return repository.findById(id).get()
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
        if(!repository.existsById(id)) {
            throw NotFoundException(notFoundMessage)
        }

        repository.deleteById(id)
    }
}