package com.atlas.asset.persistence.entities

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "asset")
data class AssetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, name = "user_id")
    val userId: Long,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val description: String,
    @Column(nullable = false)
    val category: String,
    @Column(nullable = true, name = "maturity_date")
    val maturityDate: LocalDate?,
    @Column(nullable = false)
    val currency: String,
    @Column(nullable = false, name = "risk_level")
    val riskLevel: String,
    @Column(nullable = true, name = "growth_rate")
    val growthRate: Double?,
)