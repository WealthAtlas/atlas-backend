package com.atlas.user.domain

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val password: String
)
