package com.atlas.common.dtos.auth

data class RegisterCredentialRequest(
    val userId: Long,
    val email: String,
    val password: String
)