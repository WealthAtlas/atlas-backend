package com.atlas.user.controllers

import com.atlas.common.dtos.user.RegisterUserRequest
import com.atlas.user.domain.User
import com.atlas.user.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody request: RegisterUserRequest): ResponseEntity<Unit> {
        return userService.registerUser(name = request.name, email = request.email, password = request.password).fold({
            ResponseEntity.status(HttpStatus.CREATED).build()
        }, { ResponseEntity.badRequest().build() })
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): ResponseEntity<User> {
        return userService.getByUserId(userId)
            .fold({ user -> ResponseEntity.ok(user) }, { ResponseEntity.status(HttpStatus.NOT_FOUND).build() })
    }
}