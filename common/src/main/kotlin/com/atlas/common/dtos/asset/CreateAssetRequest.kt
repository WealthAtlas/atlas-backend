package com.atlas.common.dtos.asset

import java.time.LocalDate
import java.util.*

data class CreateAssetRequest(
    val name: String,
    val userId: Long,
    val description: String,
    val category: String,
    val currency: String,
    val initialInvestmentAmount: Double,
    val initialInvestmentQty: Double?,
    val maturityDate: LocalDate?,
    val riskLevel: String,
    val growthRate: Double?,
)