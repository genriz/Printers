package com.app.printers.ui.printer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.printers.data.Repository
import com.app.printers.model.Location
import com.app.printers.model.Printer
import com.app.printers.model.Toner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrinterDetailsViewModel: ViewModel() {

    fun getAllToners(): LiveData<List<Toner>>{
        return Repository.getToners()
    }

    fun getPrinterById(id: Int): LiveData<Printer> {
        return Repository.getPrinterById(id)
    }

    fun insertPrinter(printer: Printer){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.insertPrinter(printer)
            }
        }
    }

    fun deletePrinter(printer: Printer){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.deletePrinter(printer)
            }
        }
    }

    fun getLocations(): LiveData<List<Location>>{
        return Repository.getLocations()
    }

    fun getAllPrinters(): LiveData<List<Printer>> {
        return Repository.getPrinters()
    }

}