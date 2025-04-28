package com.atlas.asset.domain

import com.atlas.asset.persistence.entities.InvestmentEntity
import com.atlas.common.models.Money
import java.time.LocalDate

data class Investment(
    val quantity: Double?,
    val pricePerQty: Money,
    val date: LocalDate
) {
    companion object {
        fun from(entity: InvestmentEntity): Investment {
            return Investment(
                quantity = entity.qty,
                pricePerQty = Money(entity.valuePerQty, entity.currency),
                date = entity.date
            )
        }
    }
}