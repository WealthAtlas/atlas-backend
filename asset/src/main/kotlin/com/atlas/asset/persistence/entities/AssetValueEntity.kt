package com.atlas.asset.persistence.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "asset_value")
data class AssetValueEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, name = "asset_id")
    val assetId: Long,
    @Column(nullable = false, name = "value_per_qty")
    val valuePerQty: Double,
    @Column(nullable = false)
    val currency: String,
    @Column(nullable = false)
    val date: LocalDate,
)