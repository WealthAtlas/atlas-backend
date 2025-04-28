package com.atlas.common.dtos.auth

data class ValidateCredentialRequest(
    val email: String, val password: String
)