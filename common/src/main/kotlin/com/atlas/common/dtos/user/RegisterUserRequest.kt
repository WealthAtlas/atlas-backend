package com.atlas.common.dtos.user

data class RegisterUserRequest(
    val name: String,
    val email: String,
    val password: String
)
