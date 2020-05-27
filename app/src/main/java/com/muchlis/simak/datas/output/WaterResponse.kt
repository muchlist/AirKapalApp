package com.muchlis.simak.datas.output

import com.squareup.moshi.Json

data class WaterResponse(
    @Json(name = "approval")
    val approval: Approval,
    @Json(name = "branch")
    val branch: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "doc_level")
    val docLevel: Int,
    @Json(name = "_id")
    val id: String,
    @Json(name = "job_number")
    val jobNumber: String,
    @Json(name = "locate")
    val locate: String,
    @Json(name = "photos")
    val photos: Photos,
    @Json(name = "suspicious")
    val suspicious: Boolean,
    @Json(name = "suspicious_note")
    val suspiciousNote: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "vessel")
    val vessel: Vessel,
    @Json(name = "volume")
    val volume: Volume
) {
    data class Approval(
        @Json(name = "approval_id")
        val approvalId: String,
        @Json(name = "approval_name")
        val approvalName: String,
        @Json(name = "approval_time")
        val approvalTime: String?,
        @Json(name = "created_by")
        val createdBy: String,
        @Json(name = "created_by_id")
        val createdById: String,
        @Json(name = "witness_name")
        val witnessName: String
    )

    data class Photos(
        @Json(name = "end_photo")
        val endPhoto: String,
        @Json(name = "start_photo")
        val startPhoto: String,
        @Json(name = "witness_photo")
        val witnessPhoto: String
    )

    data class Vessel(
        @Json(name = "agent")
        val agent: String,
        @Json(name = "int_dom")
        val intDom: String,
        @Json(name = "vessel_id")
        val vesselId: String,
        @Json(name = "vessel_name")
        val vesselName: String
    )

    data class Volume(
        @Json(name = "tonase_difference")
        val tonaseDifference: Int?,
        @Json(name = "tonase_end")
        val tonaseEnd: Int?,
        @Json(name = "tonase_end_time")
        val tonaseEndTime: String?,
        @Json(name = "tonase_ordered")
        val tonaseOrdered: Int,
        @Json(name = "tonase_real")
        val tonaseReal: Int?,
        @Json(name = "tonase_start")
        val tonaseStart: Int?,
        @Json(name = "tonase_start_time")
        val tonaseStartTime: String?
    )
}