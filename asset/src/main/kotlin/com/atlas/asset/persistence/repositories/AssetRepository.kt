package com.atlas.asset.persistence.repositories

import com.atlas.asset.persistence.entities.AssetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetRepository : JpaRepository<AssetEntity, Long> {
    fun findByUserIdAndId(userId: Long, id: Long): AssetEntity?
}