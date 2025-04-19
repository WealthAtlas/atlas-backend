package com.atlas.common.models.auth

data class ValidateCredentialRequest(
    val email: String, val password: String
)