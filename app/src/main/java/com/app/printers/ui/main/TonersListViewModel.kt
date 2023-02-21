package com.app.printers.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.printers.data.Repository
import com.app.printers.model.Toner

class TonersListViewModel: ViewModel() {

    fun getAllToners(): LiveData<List<Toner>>{
        return Repository.getToners()
    }
}