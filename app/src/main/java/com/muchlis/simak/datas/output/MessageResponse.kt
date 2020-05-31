package com.muchlis.simak.datas.output

import com.squareup.moshi.Json

data class MessageResponse(
    @Json(name = "message")
    val message: String
)