package com.muchlis.simak.services

import com.muchlis.simak.datas.input.LoginDataRequest
import com.muchlis.simak.datas.output.LoginDataResponse
import com.muchlis.simak.datas.output.WaterListResponse
import com.muchlis.simak.utils.App
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private var BASE_URL = App.prefs.baseUrl

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @POST("/login")
    fun postLogin(
        @Body args: LoginDataRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Call<LoginDataResponse>

    /*/api/waters?branch=BAGENDANG&agent=&page=1&search=MERA*/
    @GET("/api/waters")
    fun getWaters(
        @Header("Authorization") token: String,
        @Query("branch") branch: String,
        @Query("agent") agent: String = "",
        @Query("search") search: String = "",
        @Query("page") page: Int = 1
    ): Call<WaterListResponse>


    /*//USER
    @POST("/login")
    fun postLogin(
        @Body args: LoginDataInput,
        @Header("Content-Type") contentType: String = "application/json"
    ): Call<LoginDataResponse>



    @GET("/profile")
    fun getProfile(
        @Header("Authorization") token: String
    ): Call<ProfileDataResponse>

    *//*branch=BAGENDANG&document_level=23&agent=&page=1*//*
    @GET("/api/containers")
    fun getContainer(
        @Header("Authorization") token: String,
        @Query("branch") branch: String,
        @Query("document_level") documentLvl: Int = 0,
        @Query("agent") agent: String,
        @Query("search") search: String = "",
        @Query("page") page: Int = 1
    ): Call<ContainerListDataResponse>

    @GET("/vessels")
    fun getVessels(
        @Header("Authorization") token: String,
        @Query("search") search: String = ""
    ): Call<VesselListDataResponse>

    @POST("/vessels")
    fun postVessel(
        @Header("Authorization") token: String,
        @Body args: InsertVesselDataInput
    ): Call<MessageResponse>

    @POST("/api/containers")
    fun postContainer(
        @Header("Authorization") token: String,
        @Body args: InsertContainerDataInput
    ): Call<MessageResponse>

    @PUT("/api/containers/{id}")
    fun putContainer(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body args: PutContainerInfoDataInput
    ): Call<ContainerListDataResponse.Container>

    @PUT("/api/containers-change-vessel/{id}")  //AIAIAIAOSIANSIANSIUIUASIOANISNIOANS
    fun putVesselInContainer(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body args: PutContainerInfoVesselDataInput
    ): Call<ContainerListDataResponse.Container>

    @DELETE("/api/containers/{id}")
    fun deleteContainer(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<MessageResponse>

    @GET("/api/containers/{id}")
    fun getContainerInfoDetail(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<ContainerListDataResponse.Container>

    @POST("/api/pass-check/{containerid}/{step}/{activity}")
    fun postContainerCheckByPass(
        @Path("containerid") containerid: String,
        @Path("step") step: String,
        @Path("activity") activity: String,
        @Header("Authorization") token: String,
        @Body args: PostTimeStampDataInput
    ): Call<ContainerListDataResponse.Container>

    @POST("/api/create-check/{id}/{step}")
    fun postContainerCheck(
        @Path("id") id: String,
        @Path("step") step: String,
        @Header("Authorization") token: String,
        @Body args: InsertContainerCheckDataInput
    ): Call<ContainerCheckDataResponse>

    @GET("/api/check/{id}")
    fun getContainerCheckDetail(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<ContainerCheckDataResponse>

    @PUT("/api/check/{id}")
    fun putContainerCheckDetail(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body args: PutContainerCheckDataInput
    ): Call<ContainerCheckDataResponse>

    @POST("/api/check/{id}/witness-approve")
    fun postDocumentWitnessReady(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body args: PostTimeStampDataInput
    ): Call<ContainerCheckDataResponse>

    @POST("/api/check/{id}/ready")
    fun postDocumentReady(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body args: PostTimeStampDataInput
    ): Call<ContainerCheckDataResponse>

    @POST("/api/check/{id}/approval")
    fun postDocumentApproval(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body args: PostTimeStampDataInput
    ): Call<ContainerCheckDataResponse>

    @POST("/api/check/{id}/unready")
    fun postDocumentUnready(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body args: PostTimeStampDataInput
    ): Call<ContainerCheckDataResponse>

    @GET("/api/checks")
    fun getDocumentCheckList(
        @Header("Authorization") token: String,
        @Query("branch") branch: String
    ): Call<ContainerCheckListDataResponse>


    @Multipart
    @POST("/api/upload/image/{id}/{position}")
    fun postUploadImage(
        @Path("id") id: String,
        @Path("position") position: String,
        @Header("Authorization") token: String,
        //@Part image: MultipartBody.Part,
        @Part("image\"; filename=\"pp.jpg\" ") image: RequestBody
    ): Call<MessageResponse>

    @Multipart
    @POST("/api/upload/profil-image")
    fun postUploadProfileImage(
        @Header("Authorization") token: String,
        @Part("image\"; filename=\"pp.jpg\" ") image: RequestBody
    ): Call<MessageResponse>


    @GET("/api/copy-photo-to/{id}")
    fun copyImageFromAnotherCheck(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<ContainerCheckDataResponse>

    @GET("/all-agent")
    fun getAgentList(
        @Header("Authorization") token: String
    ): Call<AgentListDataResponse>*/

}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}