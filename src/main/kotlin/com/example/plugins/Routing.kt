package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hashFunc
import com.example.models.entity.User
import com.example.repository.Repository
import com.example.routes.NoteRoutes
import com.example.routes.UserRoutes
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(jwtService: JwtService, db: Repository) {
    val hashFunction = { s:String -> hashFunc(s) }
    routing {
        UserRoutes(db, jwtService, hashFunction)
        NoteRoutes(db, hashFunction)

        get("/token") {
            val email = call.request.queryParameters["email"]!!
            val password = call.request.queryParameters["password"]!!
            val username = call.request.queryParameters["username"]!!

            val user = User(email, username, hashFunc(password))
            call.respond(jwtService.generateToken(user))
        }

    }
}
