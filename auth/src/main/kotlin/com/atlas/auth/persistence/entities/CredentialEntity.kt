package com.atlas.auth.persistence.entities

import com.atlas.auth.domain.Credential
import jakarta.persistence.*

@Entity
@Table(name = "credentials")
data class CredentialEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val userId: Long = 0,

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false, unique = false)
    val passwordHash: String = ""
) {
    fun toDomain(): Credential {
        return Credential(id = id, userId = userId, email = email, passwordHash = passwordHash)
    }

    companion object {
        fun fromDomain(credential: Credential): CredentialEntity {
            return CredentialEntity(
                id = credential.id,
                userId = credential.userId,
                email = credential.email,
                passwordHash = credential.passwordHash
            )
        }
    }
}