package com.example.models.entity

import kotlinx.serialization.descriptors.PrimitiveKind

data class User(val email: String,
                val name: String,
                val hashPassword: String)