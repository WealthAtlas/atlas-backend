package com.atlas.asset.persistence.repositories

import com.atlas.asset.persistence.entities.AssetValueEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetValueRepository : JpaRepository<AssetValueEntity, Long> {
    fun findByAssetId(assetId: Long): List<AssetValueEntity>
}