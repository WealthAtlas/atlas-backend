package com.atlas.user.persistence.entities

import com.atlas.user.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = false)
    val name: String = "",

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false, unique = false)
    val password: String = ""
) {
    fun toDomain(): User {
        return User(id = id, name = name, email = email, password = password)
    }

    companion object {
        fun fromDomain(user: User): UserEntity {
            return UserEntity(user.id, user.name, user.email, user.password)
        }
    }
}