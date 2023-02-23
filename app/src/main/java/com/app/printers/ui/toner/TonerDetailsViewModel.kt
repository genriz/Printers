package com.app.printers.ui.toner

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.printers.data.Repository
import com.app.printers.model.Location
import com.app.printers.model.Toner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TonerDetailsViewModel: ViewModel() {

    fun getAllToners(): LiveData<List<Toner>>{
        return Repository.getToners()
    }

    fun getTonerById(id: Int): LiveData<Toner> {
        return Repository.getTonerById(id)
    }

    fun insertToner(toner: Toner){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.insertToner(toner)
            }
        }
    }

    fun deleteToner(toner: Toner){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.deleteToner(toner)
            }
        }
    }

    fun getLocations(): LiveData<List<Location>>{
        return Repository.getLocations()
    }

}