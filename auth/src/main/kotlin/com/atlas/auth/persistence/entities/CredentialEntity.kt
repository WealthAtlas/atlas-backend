package com.atlas.auth.persistence.entities

import jakarta.persistence.*

@Entity
@Table(name = "credentials")
data class CredentialEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, name = "user_id")
    val userId: Long = 0,

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false, name = "password_hash")
    val passwordHash: String = ""
)