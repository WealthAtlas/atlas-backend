package com.atlas.user.persistence.entities

import com.atlas.user.domain.User
import jakarta.persistence.Entity

@Entity
class UserEntity(
    val id: Long,
    val name: String,
    val email: String,
    val password: String
) {
    fun toDomain(): User {
        return User(id, name, email, password)
    }

    companion object {
        fun fromDomain(user: User): UserEntity {
            return UserEntity(user.id, user.name, user.email, user.password)
        }
    }
}