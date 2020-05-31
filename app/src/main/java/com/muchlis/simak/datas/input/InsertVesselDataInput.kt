package com.muchlis.simak.datas.input

import com.squareup.moshi.Json

data class InsertVesselDataInput(
    @Json(name = "agent")
    val agent: String,
    @Json(name = "isInternational")
    val isInternational: Boolean,
    @Json(name = "ship_name")
    val shipName: String
)