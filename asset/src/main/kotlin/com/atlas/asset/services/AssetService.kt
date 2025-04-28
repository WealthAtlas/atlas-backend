package com.atlas.asset.services

import com.atlas.asset.domain.Asset
import com.atlas.asset.persistence.entities.AssetEntity
import com.atlas.asset.persistence.entities.AssetValueEntity
import com.atlas.asset.persistence.entities.InvestmentEntity
import com.atlas.asset.persistence.repositories.AssetRepository
import com.atlas.asset.persistence.repositories.AssetValueRepository
import com.atlas.asset.persistence.repositories.InvestmentRepository
import com.atlas.common.dtos.asset.AssetResponse
import com.atlas.common.dtos.asset.CreateAssetRequest
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AssetService(
    private val assetRepository: AssetRepository,
    private val assetValueRepository: AssetValueRepository,
    private val investmentRepository: InvestmentRepository
) {
    fun createAsset(request: CreateAssetRequest): Result<AssetResponse> {
        val assetEntity = assetRepository.save(
            AssetEntity(
                name = request.name,
                userId = request.userId,
                description = request.description,
                category = request.category,
                currency = request.currency,
                maturityDate = request.maturityDate,
                riskLevel = request.riskLevel,
                growthRate = request.growthRate
            )
        )
        val investmentEntity = investmentRepository.save(
            InvestmentEntity(
                assetId = assetEntity.id ?: 0,
                valuePerQty = request.initialInvestmentAmount / (request.initialInvestmentQty ?: 1.0),
                qty = request.initialInvestmentQty!!,
                date = LocalDate.now(),
                currency = request.currency,
            )
        )
        val assetValueEntity = assetValueRepository.save(
            AssetValueEntity(
                assetId = assetEntity.id ?: 0,
                valuePerQty = request.initialInvestmentAmount / (request.initialInvestmentQty ?: 1.0),
                date = LocalDate.now(),
                currency = request.currency,
            )
        )

        val asset = Asset.from(
            entity = assetEntity,
            investmentEntities = listOf(investmentEntity),
            assetValueEntities = listOf(assetValueEntity)
        )

        return Result.success(asset.toResponse())
    }

    fun getAssetBy(id: Long, userId: Long): Result<AssetResponse> {
        val assetEntity = assetRepository.findByUserIdAndId(userId = userId, id = id)
            ?: return Result.failure(IllegalArgumentException("Asset not found"))

        val investmentEntities = investmentRepository.findByAssetId(assetId = assetEntity.id ?: 0)
        val assetValueEntities = assetValueRepository.findByAssetId(assetId = assetEntity.id ?: 0)

        return Result.success(
            Asset.from(
                entity = assetEntity,
                investmentEntities = investmentEntities,
                assetValueEntities = assetValueEntities
            ).toResponse()
        )
    }

    fun getAssetValueAt(id: Long, userId: Long, date: LocalDate?): Result<Double> {
        val assetEntity = assetRepository.findByUserIdAndId(userId = userId, id = id)
            ?: return Result.failure(IllegalArgumentException("Asset not found"))

        val investmentEntities = investmentRepository.findByAssetId(assetId = assetEntity.id ?: 0)
        val assetValueEntities = assetValueRepository.findByAssetId(assetId = assetEntity.id ?: 0)

        return Result.success(
            Asset.from(
                entity = assetEntity,
                investmentEntities = investmentEntities,
                assetValueEntities = assetValueEntities
            ).valueAt(
                date = date ?: LocalDate.now()
            ).amount
        )
    }
}