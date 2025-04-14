package com.atlas.user.services

import arrow.core.Either
import com.atlas.user.domain.User
import com.atlas.user.persistence.entities.UserEntity
import com.atlas.user.persistence.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun registerUser(name: String, email: String, password: String): Either<String, Unit> {
        userRepository.findByEmail(email)?.let {
            return Either.Left("User already exists")
        }

        userRepository.save(
            UserEntity(
                id = 0,
                name = name,
                email = email,
                password = password
            )
        )
        return Either.Right(Unit)
    }

    fun getByUserId(userId: Long): Either<String, User> {
        val user = userRepository.findByIdOrNull(userId)
        return if (user != null) {
            Either.Right(user.toDomain())
        } else {
            Either.Left("User not found")
        }
    }
}