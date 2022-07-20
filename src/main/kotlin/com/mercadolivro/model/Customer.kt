package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity(name = "customer")
data class Customer(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus? = null
)