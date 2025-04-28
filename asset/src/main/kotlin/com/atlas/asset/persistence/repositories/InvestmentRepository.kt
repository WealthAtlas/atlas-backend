package com.atlas.asset.persistence.repositories

import com.atlas.asset.persistence.entities.InvestmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvestmentRepository : JpaRepository<InvestmentEntity, Long> {
    fun findByAssetId(assetId: Long): List<InvestmentEntity>
}