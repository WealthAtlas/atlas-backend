package com.atlas.asset.configs

import com.atlas.common.config.AbstractDataSourceConfig
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.atlas.asset.persistence.repositories"],
    entityManagerFactoryRef = "assetEntityManagerFactory",
    transactionManagerRef = "assetTransactionManager"
)
class AssetDataSourceConfig : AbstractDataSourceConfig() {
    @Value("\${asset.datasource.url}")
    private val url: String? = null

    @Value("\${asset.datasource.username}")
    private val username: String? = null

    @Value("\${asset.datasource.password}")
    private val password: String? = null

    @Value("\${asset.datasource.schema}")
    private val schema: String? = null

    @Bean(name = ["assetDataSource"])
    fun assetDataSource(): DataSource {
        return createDataSource(url, username, password)
    }

    @Bean(name = ["assetEntityManagerFactory"])
    fun assetEntityManagerFactory(
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return createEntityManagerFactory(
            builder, assetDataSource(), arrayOf("com.atlas.asset.persistence.entities"), schema, "assetPU"
        )
    }

    @Bean(name = ["assetTransactionManager"])
    fun assetTransactionManager(
        @Qualifier("assetEntityManagerFactory") emf: EntityManagerFactory
    ): JpaTransactionManager {
        return createTransactionManager(emf)
    }
}