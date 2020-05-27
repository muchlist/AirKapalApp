package com.muchlis.simak.datas.output


import com.squareup.moshi.Json

data class WaterListResponse(
    @Json(name = "waters")
    val waters: List<WaterResponse>
)