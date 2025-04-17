package com.atlas.auth.dtos.requests

data class RegisterCredentialRequest(
    val userId: Long,
    val email: String,
    val password: String
)