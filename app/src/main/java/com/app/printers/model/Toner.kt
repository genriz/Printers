package com.app.printers.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.printers.data.RoomConverter

@Entity
@TypeConverters(RoomConverter::class)
class Toner {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var count: Int = 0
    var locations: List<Location> = ArrayList()
}
