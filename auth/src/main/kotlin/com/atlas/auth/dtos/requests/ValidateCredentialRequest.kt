package com.atlas.auth.dtos.requests

data class ValidateCredentialRequest(
    val email: String, val password: String
)