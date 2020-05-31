package com.muchlis.simak.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.muchlis.simak.datas.output.WaterListResponse
import com.muchlis.simak.services.ApiService
import com.muchlis.simak.utils.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WatersViewModel(
    private val apiService: ApiService,
    application: Application
) : AndroidViewModel(application) {

    private val _waterListData: MutableLiveData<WaterListResponse> = MutableLiveData()
    fun getWaterListData(): MutableLiveData<WaterListResponse> {
        return _waterListData
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _messageFailure = MutableLiveData<String>()
    val messageFailure: LiveData<String>
        get() = _messageFailure

    init {
        _isLoading.value = false
        _messageFailure.value = ""
    }

    fun reqListWaterItems() {
        val prefs = App.prefs
        _isLoading.value = true
        _messageFailure.value = ""
        apiService.getWaters(token = prefs.authTokenSave, branch = prefs.userBranchSave)
            .enqueue(object : Callback<WaterListResponse> {
                override fun onResponse(
                    call: Call<WaterListResponse>,
                    response: Response<WaterListResponse>
                ) {
                    when {
                        response.isSuccessful -> {
                            val result = response.body()!!
                            _waterListData.postValue(result)
                            _isLoading.value = false
                        }
                        else -> {
                            _messageFailure.value = response.code().toString()
                            _isLoading.value = false
                        }
                    }
                }

                override fun onFailure(call: Call<WaterListResponse>, t: Throwable) {
                    _isLoading.value = false
                    _messageFailure.value = t.message
                }
            })
    }


}