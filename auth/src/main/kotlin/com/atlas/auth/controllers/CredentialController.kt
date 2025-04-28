package com.atlas.auth.controllers

import com.atlas.auth.services.CredentialService
import com.atlas.common.dtos.auth.RegisterCredentialRequest
import com.atlas.common.dtos.auth.TokenResponse
import com.atlas.common.dtos.auth.ValidateCredentialRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class CredentialController(private val credentialService: CredentialService) {

    @PostMapping("/register")
    fun registerCredential(@RequestBody request: RegisterCredentialRequest): ResponseEntity<Unit> {
        return credentialService.registerCredential(
            userId = request.userId, email = request.email, password = request.password
        ).fold({
            ResponseEntity.status(HttpStatus.CREATED).build()
        }, {
            ResponseEntity.badRequest().build()
        })
    }

    @PostMapping("/validate")
    fun validateCredential(@RequestBody request: ValidateCredentialRequest): ResponseEntity<TokenResponse> {
        return credentialService.validateCredential(email = request.email, password = request.password)
            .fold({ tokenResponse ->
                ResponseEntity.ok(tokenResponse)
            }, {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
            })
    }
}