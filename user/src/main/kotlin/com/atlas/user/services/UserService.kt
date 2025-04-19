package com.atlas.user.services

import com.atlas.common.clients.AuthClient
import com.atlas.user.domain.User
import com.atlas.user.persistence.entities.UserEntity
import com.atlas.user.persistence.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val authClient: AuthClient
) {

    fun registerUser(name: String, email: String, password: String): Result<Unit> {
        userRepository.findByEmail(email)?.let {
            return Result.failure(Exception("User with email $email already exists"))
        }

        val registeredUser = userRepository.save(
            UserEntity(
                id = 0,
                name = name,
                email = email,
            )
        ).toDomain()

        val registrationResult = authClient.registerCredential(
            userId = registeredUser.id,
            email = email,
            password = password
        )

        if (registrationResult.isSuccess) {
            return Result.success(Unit)
        } else {
            userRepository.deleteById(registeredUser.id)
            return Result.failure(Exception(registrationResult.exceptionOrNull()?.message))
        }
    }

    fun getByUserId(userId: Long): Result<User> {
        val user = userRepository.findByIdOrNull(userId)
        return if (user != null) {
            Result.success(user.toDomain())
        } else {
            Result.failure(Exception("User not found"))
        }
    }
}