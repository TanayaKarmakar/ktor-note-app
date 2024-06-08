package com.example.authentication

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private val hashKey = System.getenv("HASH_SECRET_KEY").toByteArray()
private val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")

fun hashFunc(password:String) : String {
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmacKey)
    return io.ktor.util.hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}