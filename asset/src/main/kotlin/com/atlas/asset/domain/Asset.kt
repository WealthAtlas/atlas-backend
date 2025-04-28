package com.atlas.asset.domain

import com.atlas.asset.persistence.entities.AssetEntity
import com.atlas.asset.persistence.entities.AssetValueEntity
import com.atlas.asset.persistence.entities.InvestmentEntity
import com.atlas.common.dtos.asset.AssetResponse
import com.atlas.common.extensions.logger
import com.atlas.common.models.Money
import java.time.LocalDate
import java.util.*
import kotlin.math.pow

class Asset(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val investments: List<Investment>,
    val values: List<AssetValue>,
    val maturityDate: LocalDate?,
    val currency: String,
    val riskLevel: String,
    val growthRate: Double?,
) {
    fun currentValue(): Money {
        return valueAt(LocalDate.now())
    }

    fun valueAt(date: LocalDate): Money {
        if (investments.isEmpty()) {
            logger.info("No investments found")
            return Money(0.0, currency)
        }

        var dateToBeConsidered = date
        if (maturityDate != null && date.isAfter(maturityDate)) {
            dateToBeConsidered = maturityDate
        }

        // Calculate total investment
        val totalInvestment = investments.sumOf { it.pricePerQty.amount * (it.quantity ?: 0.0) }

        // Calculate the time period in years from now to the given date
        val currentDate = LocalDate.now()
        val years = (dateToBeConsidered.toEpochDay() - currentDate.toEpochDay()) / 365.0

        // Calculate future value using the growth rate
        val futureValue = totalInvestment * (1 + growthRate()).pow(years)

        return Money(futureValue, currency)
    }

    fun growthRate(): Double {
        if (growthRate != null) {
            return growthRate
        }

        if (investments.isEmpty() || values.isEmpty()) {
            logger.info("Insufficient data to calculate growth rate")
            return 0.0
        }

        // Calculate the weighted average of the invested amounts
        val totalWeightedInvestment = investments.sumOf {
            val investmentValue = it.pricePerQty.amount * (it.quantity ?: 0.0)
            val daysSinceInvestment = it.date.toEpochDay()
            investmentValue * daysSinceInvestment
        }
        val totalInvestment = investments.sumOf { it.pricePerQty.amount * (it.quantity ?: 0.0) }
        val weightedAverageDate = totalWeightedInvestment / totalInvestment

        // Convert weighted average date to LocalDate
        val weightedDate = LocalDate.ofEpochDay(weightedAverageDate.toLong())

        // Get the latest value
        val latestValue = values.maxByOrNull { it.date }
        if (latestValue == null) {
            logger.info("No latest value found")
            return 0.0
        }
        val totalQuantity = investments.sumOf { it.quantity ?: 0.0 }
        val finalValue = latestValue.pricePerQty.amount * totalQuantity

        // Calculate the time period in years
        val years = (latestValue.date.toEpochDay() - weightedDate.toEpochDay()) / 365.0
        if (years <= 0) {
            logger.info("Invalid time period for growth rate calculation")
            return 0.0
        }

        // Apply the CAGR formula
        return ((finalValue / totalInvestment).pow(1 / years)) - 1
    }

    companion object {
        fun from(
            entity: AssetEntity,
            investmentEntities: List<InvestmentEntity>,
            assetValueEntities: List<AssetValueEntity>
        ): Asset {
            return Asset(
                id = entity.id ?: 0,
                name = entity.name,
                description = entity.description,
                category = entity.category,
                investments = investmentEntities.map { Investment.from(it) },
                maturityDate = entity.maturityDate,
                currency = entity.currency,
                riskLevel = entity.riskLevel,
                values = assetValueEntities.map { AssetValue.from(it) },
                growthRate = entity.growthRate
            )
        }
    }

    fun toResponse(): AssetResponse {
        return AssetResponse(
            id = id,
            name = name,
            description = description,
            category = category,
            currency = Currency.getInstance(currency),
            investedAmount = investments.sumOf { it.pricePerQty.amount * (it.quantity ?: 0.0) },
            investedQty = investments.sumOf { it.quantity ?: 0.0 },
            currentValue = currentValue().amount,
            maturityDate = maturityDate,
            riskLevel = riskLevel,
            growthRate = growthRate()
        )
    }
}
