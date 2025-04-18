package com.atlas.user.configs

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
    basePackages = ["com.atlas.user.persistence.repositories"],
    entityManagerFactoryRef = "userEntityManagerFactory",
    transactionManagerRef = "userTransactionManager"
)
class UserDataSourceConfig : AbstractDataSourceConfig() {
    @Value("\${user.datasource.url}")
    private val url: String? = null

    @Value("\${user.datasource.username}")
    private val username: String? = null

    @Value("\${user.datasource.password}")
    private val password: String? = null

    @Value("\${user.datasource.schema}")
    private val schema: String? = null

    @Bean(name = ["userDataSource"])
    fun userDataSource(): DataSource {
        return createDataSource(url, username, password)
    }

    @Bean(name = ["userEntityManagerFactory"])
    fun userEntityManagerFactory(
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return createEntityManagerFactory(
            builder, userDataSource(), arrayOf("com.atlas.user.persistence.entities"), schema, "userPU"
        )
    }

    @Bean(name = ["userTransactionManager"])
    fun userTransactionManager(
        @Qualifier("userEntityManagerFactory") emf: EntityManagerFactory
    ): JpaTransactionManager {
        return createTransactionManager(emf)
    }
}