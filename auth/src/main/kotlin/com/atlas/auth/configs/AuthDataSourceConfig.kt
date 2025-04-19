package com.atlas.auth.configs

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
    basePackages = ["com.atlas.auth.persistence.repositories"],
    entityManagerFactoryRef = "authEntityManagerFactory",
    transactionManagerRef = "authTransactionManager"
)
class AuthDataSourceConfig : AbstractDataSourceConfig() {
    @Value("\${auth.datasource.url}")
    private val url: String? = null

    @Value("\${auth.datasource.username}")
    private val username: String? = null

    @Value("\${auth.datasource.password}")
    private val password: String? = null

    @Value("\${auth.datasource.schema}")
    private val schema: String? = null

    @Bean(name = ["authDataSource"])
    fun authDataSource(): DataSource {
        return createDataSource(url, username, password)
    }

    @Bean(name = ["authEntityManagerFactory"])
    fun autEntityManagerFactory(
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return createEntityManagerFactory(
            builder, authDataSource(), arrayOf("com.atlas.auth.persistence.entities"), schema, "authPU"
        )
    }

    @Bean(name = ["authTransactionManager"])
    fun authTransactionManager(
        @Qualifier("authEntityManagerFactory") emf: EntityManagerFactory
    ): JpaTransactionManager {
        return createTransactionManager(emf)
    }
}