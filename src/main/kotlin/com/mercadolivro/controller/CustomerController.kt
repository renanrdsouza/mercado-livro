package com.mercadolivro.controller

import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.dto.PutCustomerDto
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/customers")
class CustomerController(
    val customerService: CustomerService
) {

    @GetMapping
    fun listAll(@RequestParam name: String?): ResponseEntity<List<CustomerResponse>> {
        return ResponseEntity.ok().body(customerService.listAll(name))
    }

    @GetMapping("/{id}")
    fun findBy(@PathVariable id: Long): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok().body(customerService.findBy(id))
    }

    @PostMapping
    fun insert(
        @RequestBody customerDto: CustomerResponse,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<CustomerResponse> {
        customerService.insert(customerDto)
        val uri = uriBuilder.path("/topicos").build().toUri()
        return ResponseEntity.created(uri).body(customerDto)
    }

    @PutMapping
    fun update(
        @PathVariable id: Long,
        @RequestBody customer: PutCustomerDto
    ): ResponseEntity<CustomerResponse> {
        val customerUpdated = customerService.update(id, customer)
        return ResponseEntity.ok().body(customerUpdated)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        customerService.delete(id)
    }
}