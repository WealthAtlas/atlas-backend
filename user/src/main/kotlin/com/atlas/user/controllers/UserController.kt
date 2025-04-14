package com.atlas.user.controllers

import RegisterUserRequest
import com.atlas.user.domain.User
import com.atlas.user.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody request: RegisterUserRequest): String {
        return userService.registerUser(name = request.name, email = request.email, password = request.password)
            .fold({ error -> error }, { "User created successfully" })
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): User? {
        return userService.getByUserId(userId)
            .fold({ _ -> null }, { user -> user })
    }
}