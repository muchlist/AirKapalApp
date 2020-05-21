package com.muchlis.simak.datas.input

import com.squareup.moshi.Json

data class LoginDataInput(
    @Json(name = "password")
    val password: String,
    @Json(name = "username")
    val username: String
)