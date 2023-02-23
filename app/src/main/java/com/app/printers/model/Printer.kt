package com.app.printers.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.printers.data.RoomConverter

@Entity
@TypeConverters(RoomConverter::class)
class Printer{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var manufacturer: String = ""
    var model: String = ""
    val printerDesc
        get():String {
        return "$manufacturer $model"
    }
    var serial: String = ""
    var location: Location? = null
    var tonersIds: List<Int> = ArrayList()
    var count: Int = 0
}
