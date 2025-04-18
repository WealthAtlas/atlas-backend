package com.atlas.common.models.auth

data class RegisterCredentialRequest(
    val userId: Long,
    val email: String,
    val password: String
)