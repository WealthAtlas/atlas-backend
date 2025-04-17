package com.atlas.auth.domain

data class Credential(
    val id: Long,
    val userId: Long,
    val email: String,
    val passwordHash: String
)