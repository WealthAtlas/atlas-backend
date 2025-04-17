package com.atlas.auth.controllers

import com.atlas.auth.dtos.requests.RegisterCredentialRequest
import com.atlas.auth.dtos.requests.ValidateCredentialRequest
import com.atlas.auth.dtos.responses.TokenResponse
import com.atlas.auth.services.CredentialService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/credentials")
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
    fun validateCredential(@RequestParam request: ValidateCredentialRequest): ResponseEntity<TokenResponse> {
        return credentialService.validateCredential(email = request.email, password = request.password)
            .fold({ tokenResponse ->
                ResponseEntity.ok(tokenResponse)
            }, {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
            })
    }
}