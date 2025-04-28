package com.atlas.asset.domain

import com.atlas.asset.persistence.entities.AssetValueEntity
import com.atlas.common.models.Money
import java.time.LocalDate

data class AssetValue(
    val assetId: Long,
    val pricePerQty: Money,
    val date: LocalDate,
) {
    companion object {
        fun from(entity: AssetValueEntity): AssetValue {
            return AssetValue(
                assetId = entity.assetId,
                pricePerQty = Money(entity.valuePerQty, entity.currency),
                date = entity.date
            )

        }
    }
}