package com.atlas.user.services

import com.atlas.user.domain.User
import com.atlas.user.persistence.entities.UserEntity
import com.atlas.user.persistence.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun registerUser(name: String, email: String, password: String): Result<Unit> {
        userRepository.findByEmail(email)?.let {
            return Result.failure(Exception("User with email $email already exists"))
        }

        userRepository.save(
            UserEntity(
                id = 0,
                name = name,
                email = email,
                password = password
            )
        )
        return Result.success(Unit)
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