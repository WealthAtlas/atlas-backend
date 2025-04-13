package com.atlas.user.controllers

import com.atlas.user.domain.User
import com.atlas.user.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {


     @GetMapping
     fun getAllUsers(): List<User> {
         return emptyList()
     }
}