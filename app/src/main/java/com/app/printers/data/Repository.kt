package com.app.printers.data

import androidx.lifecycle.LiveData
import com.app.printers.model.Location
import com.app.printers.model.Printer
import com.app.printers.model.Toner

class Repository {
    companion object {

        private val db = DB.getDB()

        fun getPrinters(): LiveData<List<Printer>> {
            return db.dao().getAllPrinters()
        }

        fun getPrinterById(id: Int): LiveData<Printer> {
            return db.dao().getPrinterById(id)
        }

        fun insertPrinter(printer: Printer){
            db.dao().insertPrinter(printer)
        }

        fun deletePrinter(printer: Printer){
            db.dao().deletePrinter(printer)
        }

        fun getToners(): LiveData<List<Toner>> {
            return db.dao().getAllToners()
        }

        fun getTonerById(id: Int): LiveData<Toner> {
            return db.dao().getTonerById(id)
        }

        fun insertToner(toner: Toner){
            db.dao().insertToner(toner)
        }

        fun deleteToner(toner: Toner){
            db.dao().deleteToner(toner)
        }

        fun getLocations(): LiveData<List<Location>> {
            return db.dao().getLocations()
        }

        fun getLocationById(id: Int): LiveData<Location> {
            return db.dao().getLocationById(id)
        }

        fun insertLocation(location: Location) {
            db.dao().insertLocation(location)
        }

        fun deleteLocation(location: Location){
            db.dao().deleteLocation(location)
        }
    }
}