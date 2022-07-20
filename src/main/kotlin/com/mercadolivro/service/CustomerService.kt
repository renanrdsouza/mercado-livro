package com.mercadolivro.service

import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.dto.PutCustomerDto
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val repository: CustomerRepository,
    private val bookService: BookService
) {

    fun listAll(name: String?): List<CustomerResponse> {
        name?.let {
            return repository.findByNameContaining(it)
        }

        return repository.findAll().map { customer ->
            CustomerResponse(customer.name, customer.email, customer.status)
        }.toList()
    }

    fun findBy(id: Long): CustomerResponse {
        val customer = repository.findById(id).orElseThrow { NotFoundException(Errors.ML2001.message.format(id), Errors.ML2001.code) }
        return CustomerResponse(customer.name, customer.email, customer.status)
    }

    fun findCustomer(id: Long) : Customer {
        return repository.findById(id).get()
    }

    fun insert(customerDto: CustomerResponse) {
        repository.save(Customer(null, customerDto.name, customerDto.email, status = CustomerStatus.ATIVO))
    }

    fun update(id: Long, putCustomerDto: PutCustomerDto): CustomerResponse {
        val customer = repository.findById(id).orElseThrow { NotFoundException(Errors.ML2001.message.format(id), Errors.ML2001.code) }

        customer.name = putCustomerDto.name
        customer.email = putCustomerDto.email
        repository.save(customer)

        return CustomerResponse(customer.name, customer.email, customer.status)
    }

    fun delete(id: Long) {
        val customer = repository.findById(id).get()
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO
        repository.save(customer)
    }
}