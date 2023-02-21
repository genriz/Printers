package com.app.printers.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.printers.model.Location
import com.app.printers.model.Printer
import com.app.printers.model.Toner

@Dao
interface DAO {
    @Query("SELECT * FROM printer")
    fun getAllPrinters(): LiveData<List<Printer>>

    @Query("SELECT * FROM printer WHERE id=:id")
    fun getPrinterById(id:Int): LiveData<Printer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrinter(printer: Printer)

    @Delete
    fun deletePrinter(printer: Printer)

    @Query("SELECT * FROM toner")
    fun getAllToners(): LiveData<List<Toner>>

    @Query("SELECT * FROM toner WHERE id=:id")
    fun getTonerById(id:Int): LiveData<Toner>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToner(toner: Toner)

    @Delete
    fun deleteToner(toner: Toner)

    @Query("SELECT * FROM location")
    fun getLocations(): LiveData<List<Location>>

    @Query("SELECT * FROM location WHERE id=:id")
    fun getLocationById(id: Int): LiveData<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: Location)

    @Delete
    fun deleteLocation(location: Location)

}