package com.app.printers.viewmodel

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

class TonerDetailsViewModel: ViewModel() {

    var currentLocations = ArrayList<Location>()
    var currentToner = Toner()
    private var allToners = ArrayList<Toner>()
    private var isEdit = true

    init {
        getAllToners()
    }

    fun initCurrentToner(toner: Toner, b: Boolean){
        isEdit = b
        currentToner = toner
        currentLocations.addAll(currentToner.locations)
    }

    private fun getAllToners(){
        allToners.clear()
        allToners.addAll(Repository.getToners().value?:ArrayList())
    }

    fun getTonerById(id: Int): LiveData<Toner> {
        return Repository.getTonerById(id)
    }

    private fun insertToner(toner: Toner){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.insertToner(toner)
            }
        }
    }

    fun deleteToner(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Repository.deleteToner(currentToner)
            }
        }
    }

    fun getLocations(): LiveData<List<Location>>{
        return Repository.getLocations()
    }

    fun getAllPrinters(): LiveData<List<Printer>> {
        return Repository.getPrinters()
    }

    fun getCurrentPrinters(printers: List<Printer>): ArrayList<Printer>{
        val currentPrinters = ArrayList<Printer>()
        printers.forEach { printer->
            if (printer.tonersIds.contains(currentToner.id)){
                currentPrinters.add(printer)
            }
        }
        return currentPrinters
    }

    fun getDialogLocationsList(locations: List<Location>): ArrayList<Location>{
        val locationsList = ArrayList<Location>()
        locations.forEach { location ->
            var exist = false
            currentLocations.forEach{ l->
                if (l.id==location.id){
                    exist = true
                }
            }
            if (!exist){
                locationsList.add(location)
            }
        }
        return locationsList
    }

    fun validateToner(): Boolean{
        return if (currentToner.name.isNotEmpty()) {
            var exists = false
            allToners.forEach {
                if (currentToner.name==it.name){
                    exists = true
                }
            }
            if (!isEdit&&exists){
                false
            } else {
                currentToner.locations = currentLocations
                insertToner(currentToner)
                true
            }
        } else false
    }

    fun removeLocation(location: Location) {
        currentLocations.remove(location)
        currentToner.count -= location.tonerCount
    }

    fun addToner() {
        currentToner.count++
    }

    fun removeToner() {
        currentToner.count--
    }

    fun addLocation(location: Location) {
        currentLocations.add(location)
    }

}