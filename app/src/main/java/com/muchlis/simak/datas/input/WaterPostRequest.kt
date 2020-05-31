package com.muchlis.simak.datas.input


import com.squareup.moshi.Json

data class WaterPostRequest(
    @Json(name = "agent")
    val agent: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "int_dom")
    val intDom: String,
    @Json(name = "job_number")
    val jobNumber: String,
    @Json(name = "locate")
    val locate: String,
    @Json(name = "tonase_ordered")
    val tonaseOrdered: Int,
    @Json(name = "vessel_id")
    val vesselId: String,
    @Json(name = "vessel_name")
    val vesselName: String
)