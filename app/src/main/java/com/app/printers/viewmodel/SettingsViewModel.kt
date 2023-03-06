package com.app.printers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.printers.data.Repository
import com.app.printers.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel: ViewModel() {

    fun getLocations(): LiveData<List<Location>>{
        return Repository.getLocations()
    }

    fun getLocationById(id: Int): LiveData<Location> {
        return Repository.getLocationById(id)
    }

    fun insertLocation(location: Location){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.insertLocation(location)
            }
        }
    }

    fun deleteLocation(location: Location){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.deleteLocation(location)
            }
        }
    }
}