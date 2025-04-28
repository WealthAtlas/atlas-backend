package com.atlas.asset.controllers

import com.atlas.asset.services.AssetService
import com.atlas.common.dtos.asset.AssetResponse
import com.atlas.common.dtos.asset.CreateAssetRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/assets")
class AssetController(private val assetService: AssetService) {

    @PostMapping
    fun createAsset(@RequestBody request: CreateAssetRequest): ResponseEntity<AssetResponse> {
        return assetService.createAsset(request)
            .fold(
                { asset -> ResponseEntity.status(HttpStatus.CREATED).body(asset) },
                { ResponseEntity.status(HttpStatus.BAD_REQUEST).build() })
    }

    @GetMapping("/{id}")
    fun getAssetById(@PathVariable id: Long, @RequestParam("userId") userId: Long): ResponseEntity<AssetResponse> {
        return assetService.getAssetBy(id, userId)
            .fold(
                { asset -> ResponseEntity.ok(asset) },
                { ResponseEntity.status(HttpStatus.NOT_FOUND).build() })
    }

    @GetMapping("/{id}/value")
    fun getAssetValue(
        @PathVariable id: Long,
        @RequestParam("userId") userId: Long,
        @RequestParam("value_at", required = false) valueAt: LocalDate?
    ): ResponseEntity<Double> {
        return assetService.getAssetValueAt(id, userId, valueAt)
            .fold(
                { asset -> ResponseEntity.ok(asset) },
                { ResponseEntity.status(HttpStatus.NOT_FOUND).build() })
    }
}