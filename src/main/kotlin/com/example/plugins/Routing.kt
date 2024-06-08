package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hashFunc
import com.example.models.entity.User
import com.example.repository.Repository
import com.example.routes.UserRoutes
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val jwtService = JwtService()
    val db = Repository()
    val hashFunction = { s:String -> hashFunc(s) }
    routing {
        UserRoutes(db, jwtService, hashFunction)

        get("/token") {
            val email = call.request.queryParameters["email"]!!
            val password = call.request.queryParameters["password"]!!
            val username = call.request.queryParameters["username"]!!

            val user = User(email, username, hashFunc(password))
            call.respond(jwtService.generateToken(user))
        }

        route("/notes") {
            get {
                val id = call.request.queryParameters["id"]
                call.respond("$id")
            }

            get("/{id}") {
                val id = call.parameters["id"]
                call.respond(id.toString())
            }

            post {
                val body = call.receive<String>()
                call.respond(body)
            }

            delete {
                val body = call.receive<String>()
                call.respond(body)
            }
        }
    }
}
