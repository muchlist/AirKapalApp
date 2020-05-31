package com.muchlis.simak.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.muchlis.simak.datas.input.WaterPostRequest
import com.muchlis.simak.datas.output.MessageResponse
import com.muchlis.simak.datas.output.VesselListDataResponse
import com.muchlis.simak.services.ApiService
import com.muchlis.simak.utils.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WatersAddViewModel(
    private val apiService: ApiService,
    application: Application
) : AndroidViewModel(application) {

    //Data untuk RecyclerView
    private val _vesselData: MutableLiveData<VesselListDataResponse> = MutableLiveData()
    fun getVesselData(): MutableLiveData<VesselListDataResponse> {
        return _vesselData
    }

    private val _isVesselLoading = MutableLiveData<Boolean>()
    val isVesselLoading: LiveData<Boolean>
        get() = _isVesselLoading

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _messageFailure = MutableLiveData<String>()
    val messageFailure: LiveData<String>
        get() = _messageFailure

    private val _messageSuccess = MutableLiveData<String>()
    val messageSuccess: LiveData<String>
        get() = _messageSuccess

    private val _isInsertSuccessfull = MutableLiveData<Boolean>()
    val isInsertSuccessfull: LiveData<Boolean>
        get() = _isInsertSuccessfull

    init {
        _isLoading.value = false
        _isVesselLoading.value = false
        _messageFailure.value = ""
        _messageSuccess.value = ""
    }


    fun getVessel(search: String) {
        _isVesselLoading.value = true
        _messageSuccess.value = ""
        _messageFailure.value = ""
        apiService.getVessels(token = App.prefs.authTokenSave, search = search)
            .enqueue(object : Callback<VesselListDataResponse> {
                override fun onResponse(
                    call: Call<VesselListDataResponse>,
                    response: Response<VesselListDataResponse>
                ) {
                    when {
                        response.isSuccessful -> {
                            _isVesselLoading.value = false
                            val result = response.body()!!
                            _vesselData.postValue(result)
                        }
                        response.code() == 422 || response.code() == 401 -> {
                            //TOKEN INVALID ATAU EXPIRED
                            App.prefs.authTokenSave = ""
                            _messageFailure.value = "Token Expired"
                        }
                        response.code() == 400 -> {
                            _messageFailure.value = "Gagal mendapatkan data"
                            _isVesselLoading.value = false
                        }
                        else -> {
                            _messageFailure.value = response.body().toString()
                            _isVesselLoading.value = false
                        }
                    }
                }

                override fun onFailure(call: Call<VesselListDataResponse>, t: Throwable) {
                    _isVesselLoading.value = false
                    _messageFailure.value = "Gagal terhubung ke server"
                }
            })
    }

    fun insertWaterToServer(dataInsert: WaterPostRequest) {
        _isLoading.value = true
        _messageSuccess.value = ""
        _messageFailure.value = ""
        apiService.postContainer(token = App.prefs.authTokenSave, args = dataInsert)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    when {
                        response.isSuccessful -> {
                            _isLoading.value = false
                            _messageSuccess.value = "Dokumen pengisian air kapal berhasil ditambahkan"
                            _isInsertSuccessfull.value = true
                        }
                        response.code() == 403 -> {
                            _messageFailure.value =
                                "User tidak memiliki hak akses untuk memasukkan dokumen"
                            _isLoading.value = false
                        }
                        response.code() == 422 || response.code() == 401 -> {
                            //TOKEN INVALID ATAU EXPIRED
                            App.prefs.authTokenSave = ""
                            _messageFailure.value = "Token Expired"
                        }
                        response.code() == 400 -> {
                            _messageFailure.value = response.message()
                            _isLoading.value = false
                        }
                        else -> {
                            _messageFailure.value = "Gagal " + response.code().toString()
                            _isLoading.value = false
                        }
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    _isLoading.value = false
                    _messageFailure.value = "Gagal terhubung ke server"
                }
            })
    }

}