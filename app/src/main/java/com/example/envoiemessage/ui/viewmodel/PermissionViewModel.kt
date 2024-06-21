package com.example.envoiemessage.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PermissionViewModel(application:Application) : AndroidViewModel(application) {
    private val _hasPermission = MutableLiveData<Boolean>()
    val hasPermission: LiveData<Boolean> get() = _hasPermission

    fun checkPermission(){
        viewModelScope.launch{
            val permissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            val isGranted = permissions.all { permission ->
                ContextCompat.checkSelfPermission(
                    getApplication(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }

            _hasPermission.value = isGranted
        }
    }

    fun onPermissionResult(granted : Boolean){
        _hasPermission.value = granted
    }
}