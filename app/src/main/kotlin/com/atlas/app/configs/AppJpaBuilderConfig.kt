package com.atlas.app.configs

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaVendorAdapter

@Configuration
class AppJpaBuilderConfig {

    @Bean
    fun jpaVendorAdapter(): JpaVendorAdapter {
        val adapter = org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter()
        adapter.setShowSql(true)
        adapter.setGenerateDdl(true)
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect") // or make it dynamic
        return adapter
    }

    @Bean
    fun entityManagerFactoryBuilder(
        jpaVendorAdapter: JpaVendorAdapter,
        jpaProperties: JpaProperties
    ): EntityManagerFactoryBuilder {
        return EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties.properties, null)
    }
}