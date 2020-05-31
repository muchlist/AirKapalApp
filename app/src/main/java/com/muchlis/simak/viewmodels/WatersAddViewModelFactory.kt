package com.muchlis.simak.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muchlis.simak.services.ApiService

@Suppress("UNCHECKED_CAST")
class WatersAddViewModelFactory(
    private val apiService: ApiService,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("uncheked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatersAddViewModel::class.java)) {
            return WatersAddViewModel(apiService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}