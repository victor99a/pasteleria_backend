
package com.pasteleria.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

object Database {

    // 1️⃣ Leemos variables de entorno de Railway (si existen)
    private val host: String = System.getenv("MYSQLHOST") ?: "localhost"
    private val port: String = System.getenv("MYSQLPORT") ?: "3306"
    private val dbName: String = System.getenv("MYSQLDATABASE") ?: "pasteleria_db"
    private val user: String = System.getenv("MYSQLUSER") ?: "root"
    private val password: String = System.getenv("MYSQLPASSWORD") ?: ""

    // 2️⃣ Armamos el JDBC URL
    private val jdbcUrl: String =
        "jdbc:mysql://$host:$port/$dbName?useSSL=false&serverTimezone=UTC"

    // 3️⃣ Config de Hikari con ese URL
    private val config = HikariConfig().apply {
        this.jdbcUrl = jdbcUrl
        this.username = user
        this.password = password
        this.maximumPoolSize = 5
        this.driverClassName = "com.mysql.cj.jdbc.Driver"
    }

    private val ds = HikariDataSource(config)

    fun connection(): Connection = ds.connection
}
