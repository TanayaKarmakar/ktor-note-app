package com.example.repository

import com.example.models.entity.User
import com.example.models.tables.UserTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserRepository {
    suspend fun addUser(user: User) =
        dbQuery{
            UserTable.insert { ut ->
                ut[UserTable.email] = user.email
                ut[UserTable.name] = user.name
                ut[UserTable.hashPassword] = user.hashPassword
            }
        }


    suspend fun findUserByEmail(email: String) = dbQuery {
            UserTable.select {
                UserTable.email.eq(email)
            }.map{rowToUser(it)}.singleOrNull()
        }


    private fun rowToUser(row: ResultRow?): User? {
        if(row == null)
            return null
        return User(
                email = row[UserTable.email],
                name = row[UserTable.name],
                hashPassword = row[UserTable.hashPassword]
        )
    }
}