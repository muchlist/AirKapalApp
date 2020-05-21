package com.muchlis.simak.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.muchlis.simak.datas.input.LoginDataRequest
import com.muchlis.simak.datas.output.LoginDataResponse
import com.muchlis.simak.services.ApiService
import com.muchlis.simak.utils.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val apiService: ApiService,
    application: Application
) : AndroidViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean>
        get() = _isLoginSuccess

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String>
        get() = _isError

    init {
        _isLoading.value = false
        _isLoginSuccess.value = false
        _isError.value = ""
    }


    fun postLogin(loginInput: LoginDataRequest) {
        _isLoading.value = true
        _isError.value = ""
        apiService.postLogin(
            args = loginInput
        ).enqueue(object : Callback<LoginDataResponse> {
            override fun onResponse(
                call: Call<LoginDataResponse>,
                response: Response<LoginDataResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        val result = response.body()!!
                        saveDataLogin(result)
                        _isLoginSuccess.value = true
                    }
                    response.code() == 400 -> {
                        _isError.value = "Username atau password salah!"
                        _isLoading.value = false
                    }
                    else -> {
                        _isError.value = response.code().toString()
                        _isLoading.value = false
                    }
                }
            }

            override fun onFailure(call: Call<LoginDataResponse>, t: Throwable) {
                _isLoginSuccess.value = false
                _isLoading.value = false
                _isError.value = t.message
            }
        })
    }

    private fun saveDataLogin(data: LoginDataResponse) {
        val pref = App.prefs
        pref.authTokenSave = "Bearer "+ data.accessToken
        pref.nameSave = data.name
        pref.userBranchSave = data.branch
        pref.companySave = data.company
        pref.isAdmin = data.isAdmin
        pref.isTally = data.isTally
        pref.isManager = data.isManager
        pref.isAgent = data.isAgent
    }

}