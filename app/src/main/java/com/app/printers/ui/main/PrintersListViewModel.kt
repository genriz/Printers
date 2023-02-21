package com.app.printers.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.printers.data.Repository
import com.app.printers.model.Printer

class PrintersListViewModel: ViewModel() {

    fun getAllPrinters(): LiveData<List<Printer>> {
        return Repository.getPrinters()
    }

}