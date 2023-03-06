package com.app.printers.viewmodel

import android.content.Context
import android.widget.ArrayAdapter
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

    var toners = ArrayList<Toner>()
    private var tonersIds = ArrayList<Int>()
    var currentPrinter = Printer()
    private var allPrinters = ArrayList<Printer>()
    private var isEdit = false
    private var printerNameList = mutableListOf<String>()
    private var printerManufactureList = mutableListOf<String>()
    private var printerModelList = mutableListOf<String>()

    fun initCurrentPrinter(printer: Printer, isEdit: Boolean){
        this.isEdit = isEdit
        currentPrinter = printer
    }

    fun getAllToners(): LiveData<List<Toner>>{
        return Repository.getToners()
    }

    fun getPrinterById(id: Int): LiveData<Printer> {
        return Repository.getPrinterById(id)
    }

    private fun insertPrinter(printer: Printer){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.insertPrinter(printer)
            }
        }
    }

    fun deletePrinter(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.deletePrinter(currentPrinter)
            }
        }
    }

    fun getLocations(): LiveData<List<Location>>{
        return Repository.getLocations()
    }

    fun getAllPrinters(): LiveData<List<Printer>> {
        val printers = Repository.getPrinters()
        allPrinters.clear()
        allPrinters.addAll(printers.value?:ArrayList())
        for (printer in allPrinters){
            printerNameList.add(printer.name)
            printerManufactureList.add(printer.manufacturer)
            printerModelList.add(printer.model)
        }
        return printers
    }

    fun getAdapterName(context: Context):ArrayAdapter<String>{
        return ArrayAdapter(context,
                androidx.appcompat.R.layout.select_dialog_item_material,
                printerNameList)
    }

    fun getAdapterManufacture(context: Context):ArrayAdapter<String>{
        return ArrayAdapter(context,
            androidx.appcompat.R.layout.select_dialog_item_material,
            printerManufactureList)
    }

    fun getAdapterModel(context: Context):ArrayAdapter<String>{
        return ArrayAdapter(context,
            androidx.appcompat.R.layout.select_dialog_item_material,
            printerModelList)
    }

    fun getPrinterToners(toners: List<Toner>): ArrayList<Toner>{
        toners.forEach { toner->
            if (currentPrinter.tonersIds.contains(toner.id)){
                this.toners.add(toner)
            }
        }
        return this.toners
    }

    fun getTonersList(allToners: List<Toner>): List<Toner> {
        val tonersList = ArrayList<Toner>()
        allToners.forEach { toner ->
            var exist = false
            toners.forEach{ t->
                if (t.id==toner.id){
                    exist = true
                }
            }
            if (!exist){
                tonersList.add(toner)
            }
        }
        return tonersList
    }

    fun validatePrinter(): Boolean {
        if (currentPrinter.name.isNotEmpty()||
            currentPrinter.manufacturer.isNotEmpty()||
            currentPrinter.model.isNotEmpty()) {
            var exists = false
            allPrinters.forEach {
                if (currentPrinter.name==it.name){
                    exists = true
                }
            }
            return if (!isEdit&&exists){
                false
            } else {
                currentPrinter.tonersIds = tonersIds
                insertPrinter(currentPrinter)
                true
            }
        } else return false
    }

    fun removeToner(toner: Toner) {
        tonersIds.remove(toner.id)
        toners.remove(toner)
    }

    fun addToner(toner: Toner) {
        tonersIds.add(toner.id)
        toners.add(toner)
    }

    fun setPrinterLocation(location: Location) {
        currentPrinter.location = location
    }

}