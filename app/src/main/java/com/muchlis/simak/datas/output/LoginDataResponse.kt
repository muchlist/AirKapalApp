package com.muchlis.simak.datas.output

import com.squareup.moshi.Json

data class LoginDataResponse(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "branch")
    val branch: String,
    @Json(name = "company")
    val company: String,
    @Json(name = "isAdmin")
    val isAdmin: Boolean,
    @Json(name = "isAgent")
    val isAgent: Boolean,
    @Json(name = "isManager")
    val isManager: Boolean,
    @Json(name = "isTally")
    val isTally: Boolean,
    @Json(name = "name")
    val name: String
)