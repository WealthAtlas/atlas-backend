package com.atlas.common.models.user

data class RegisterUserRequest(
    val name: String,
    val email: String,
    val password: String
)
