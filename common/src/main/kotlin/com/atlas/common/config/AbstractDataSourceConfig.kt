package com.atlas.common.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource

abstract class AbstractDataSourceConfig {
    protected fun createDataSource(url: String?, username: String?, password: String?): DataSource {
        return DataSourceBuilder.create().url(url).username(username).password(password)
            .driverClassName("org.postgresql.Driver").build()
    }

    protected fun createEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        dataSource: DataSource?,
        packagesToScan: Array<String?>,
        schema: String?,
        persistenceUnitName: String?
    ): LocalContainerEntityManagerFactoryBean {
        val jpaProps: MutableMap<String, Any?> = HashMap()
        jpaProps["hibernate.hbm2ddl.auto"] = "update"
        jpaProps["hibernate.dialect"] = "org.hibernate.dialect.PostgreSQLDialect"
        jpaProps["hibernate.default_schema"] = schema

        return builder.dataSource(dataSource).packages(*packagesToScan).persistenceUnit(persistenceUnitName)
            .properties(jpaProps).build()
    }

    protected fun createTransactionManager(emf: EntityManagerFactory): JpaTransactionManager {
        return JpaTransactionManager(emf)
    }
}