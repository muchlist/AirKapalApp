package com.muchlis.simak.datas.output

import com.squareup.moshi.Json

data class VesselListDataResponse(
    @Json(name = "vessels")
    val vessels: List<Vessel>
) {
    data class Vessel(
        @Json(name = "agent")
        val agent: String,
        @Json(name = "_id")
        val id: String,
        @Json(name = "isActive")
        val isActive: Boolean,
        @Json(name = "isInternational")
        val isInternational: Boolean,
        @Json(name = "ship_name")
        val shipName: String
    )
}