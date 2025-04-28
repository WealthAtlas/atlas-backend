package com.atlas.common.dtos.asset

import java.time.LocalDate
import java.util.*

data class AssetResponse(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val currency: Currency,
    val investedAmount: Double,
    val investedQty: Double?,
    val currentValue: Double,
    val maturityDate: LocalDate?,
    val riskLevel: String,
    val growthRate: Double,
)