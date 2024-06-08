package com.example


import com.example.authentication.JwtService
import com.example.plugins.*
import com.example.repository.DatabaseFactory
import com.example.repository.Repository
import io.ktor.server.application.*
import io.ktor.server.locations.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val jwtService = JwtService()
    val db = Repository()
    DatabaseFactory.init()
    install(Locations)
    configureSerialization()
    configureSecurity(jwtService, db)
    configureRouting(jwtService, db)

}
