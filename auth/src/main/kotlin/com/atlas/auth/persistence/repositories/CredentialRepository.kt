package com.atlas.auth.persistence.repositories

import com.atlas.auth.persistence.entities.CredentialEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CredentialRepository : JpaRepository<CredentialEntity, Long> {
    fun findByEmail(email: String): CredentialEntity?
}