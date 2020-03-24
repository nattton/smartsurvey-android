package com.cdd.smartsurvey.http.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class LoginResponse(
        val activation: String,
        val addr: String,
        val addr2: String,
        val fname: String,
        val idcard: String,
        val lname: String,
        val message: String,
        val photo: String,
        val position: String,
        val status: String,
        val token: String
) {
    class Deserializer : ResponseDeserializable<LoginResponse> {
        override fun deserialize(content: String): LoginResponse? = Gson().fromJson(content, LoginResponse::class.java)
    }
}